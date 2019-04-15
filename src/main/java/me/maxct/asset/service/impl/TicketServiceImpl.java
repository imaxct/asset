package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import me.maxct.asset.domain.*;
import me.maxct.asset.domain.Process;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.TicketVO;
import me.maxct.asset.enumerate.PropertyStatus;
import me.maxct.asset.enumerate.TicketStatus;
import me.maxct.asset.mapper.*;
import me.maxct.asset.service.TicketService;

/**
 * @author imaxct
 * 2019-03-26 21:04
 */
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketDao     ticketDao;
    private final ProcessDao    processDao;
    private final PropertyDao   propertyDao;
    private final ProcessLogDao processLogDao;
    private final RoleDao       roleDao;

    @Override
    @Transactional
    public Msg submitTicket(Ticket ticket, User user) {

        Optional<Process> processOptional = processDao.findById(ticket.getProcessId());
        Assert.isTrue(processOptional.isPresent(), "记录不存在");
        Process process = processOptional.get();

        Optional<Property> propertyOptional = propertyDao.findById(ticket.getPropertyId());
        Assert.isTrue(propertyOptional.isPresent(), "记录不存在");
        Property property = propertyOptional.get();

        if (!StringUtils.isEmpty(process.getRoleRequired())) {
            Optional<Role> roleOptional = roleDao.findById(user.getRoleId());
            Assert.isTrue(roleOptional.isPresent(), "参数错误, 角色不存在");
            String[] arr = process.getRoleRequired().split(",");
            boolean exists = false;
            for (String role : arr) {
                if (role.equals(roleOptional.get().getRoleName())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                return Msg.err("你没有权限提交该流程");
            }
        }

        // 提交工单时，需要校验Process的initialStatus与Property的当前状态是否一致
        //todo status
        if (process.getInitialStatus() != null
            && process.getInitialStatus() != property.getCurStatus()) {
            return Msg.err("当前资产状态不满足, 不能申请当前流程");
        }

        if (process.getFinalStatus() == null && ticket.getFinalStatus() == null) {
            return Msg.err("未选择资产最终状态");
        }

        property.setCurStatus(PropertyStatus.PROCESSING);
        property.setProcessId(process.getId());
        property.setGmtModified(LocalDateTime.now());

        propertyDao.saveAndFlush(property);

        ticket.setGmtCreate(LocalDateTime.now());
        ticket.setGmtModified(LocalDateTime.now());
        ticket.setCurStatus(TicketStatus.PROCESSING);
        ticket.setCurStepId(process.getFirstStepId());
        ticketDao.saveAndFlush(ticket);
        return Msg.ok(null);
    }

    @Override
    public Msg getTicketByUserId(Long applyUserId) {
        return Msg.ok(ticketDao.getByApplyUserId(applyUserId));
    }

    @Override
    public Msg getTicketDetail(Long ticketId) {
        TicketVO ticketVO = new TicketVO();
        Optional<Ticket> ticketOptional = ticketDao.findById(ticketId);
        Assert.isTrue(ticketOptional.isPresent(), "记录不存在");
        Ticket ticket = ticketOptional.get();

        Optional<Property> propertyOptional = propertyDao.findById(ticket.getPropertyId());
        Assert.isTrue(propertyOptional.isPresent(), "记录不存在");
        Property property = propertyOptional.get();

        ticketVO.setTicket(ticket);
        ticketVO.setLogs(processLogDao.findByTicketId(ticketId));
        ticketVO.setPropertyId(property.getPropertyId());
        ticketVO.setPropertyName(property.getName());

        return Msg.ok(ticketVO);
    }

    @Autowired
    public TicketServiceImpl(TicketDao ticketDao, ProcessDao processDao, PropertyDao propertyDao,
                             ProcessLogDao processLogDao, RoleDao roleDao) {
        this.ticketDao = ticketDao;
        this.processDao = processDao;
        this.propertyDao = propertyDao;
        this.processLogDao = processLogDao;
        this.roleDao = roleDao;
    }
}
