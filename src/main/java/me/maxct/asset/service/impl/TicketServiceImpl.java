package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import me.maxct.asset.domain.Process;
import me.maxct.asset.domain.Property;
import me.maxct.asset.domain.Ticket;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.TicketVO;
import me.maxct.asset.enumerate.PropertyStatus;
import me.maxct.asset.enumerate.TicketStatus;
import me.maxct.asset.mapper.ProcessDao;
import me.maxct.asset.mapper.ProcessLogDao;
import me.maxct.asset.mapper.PropertyDao;
import me.maxct.asset.mapper.TicketDao;
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

    @Override
    @Transactional
    public Msg submitTicket(Ticket ticket) {

        Optional<Process> processOptional = processDao.findById(ticket.getProcessId());
        Assert.isTrue(processOptional.isPresent(), "记录不存在");
        Process process = processOptional.get();

        Optional<Property> propertyOptional = propertyDao.findById(ticket.getPropertyId());
        Assert.isTrue(propertyOptional.isPresent(), "记录不存在");
        Property property = propertyOptional.get();

        // 提交工单时，需要校验Process的initialStatus与Property的当前状态是否一致
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

        ticketVO.setTicket(ticketOptional.get());
        ticketVO.setLogs(processLogDao.findByTicketId(ticketId));

        return Msg.ok(ticketVO);
    }

    @Autowired
    public TicketServiceImpl(TicketDao ticketDao, ProcessDao processDao, PropertyDao propertyDao,
                             ProcessLogDao processLogDao) {
        this.ticketDao = ticketDao;
        this.processDao = processDao;
        this.propertyDao = propertyDao;
        this.processLogDao = processLogDao;
    }
}
