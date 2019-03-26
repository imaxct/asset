package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import me.maxct.asset.domain.*;
import me.maxct.asset.domain.Process;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.enumerate.PropertyStatus;
import me.maxct.asset.enumerate.TicketStatus;
import me.maxct.asset.mapper.*;
import me.maxct.asset.service.ProcessLogService;

/**
 * @author imaxct
 * 2019-03-26 10:37
 */
@Service
public class ProcessLogServiceImpl implements ProcessLogService {
    private final TicketDao     ticketDao;
    private final ProcessDao    processDao;
    private final StepDao       stepDao;
    private final RoleDao       roleDao;
    private final PropertyDao   propertyDao;
    private final ProcessLogDao processLogDao;

    @Override
    @Transactional
    public Msg processStep(ProcessLog processLog) {
        Optional<Ticket> ticketOptional = ticketDao.findById(processLog.getTicketId());
        Assert.isTrue(ticketOptional.isPresent(), "记录不存在");
        Ticket ticket = ticketOptional.get();
        Optional<Step> stepOptional = stepDao.findById(processLog.getStepId());
        Assert.isTrue(stepOptional.isPresent(), "记录不存在");
        Step step = stepOptional.get();

        // 当前流程需要等于stepId
        if (stepOptional.get().getId().compareTo(ticketOptional.get().getCurStepId()) != 0) {
            return Msg.err("流程不正确");
        }

        // 流程发起人不能进行操作
        if (ticketOptional.get().getApplyUserId().compareTo(processLog.getProcessUserId()) == 0) {
            return Msg.err("流程发起人不能进行操作");
        }

        // 当前step权限检查
        List<Role> roles = roleDao.getUserRoles(processLog.getProcessUserId());
        long count = roles.stream()
            .filter(it -> stepOptional.get().getRoleRequired().contains(it.getRoleName())).count();
        if (count <= 0) {
            return Msg.err("您没有权限进行当前操作");
        }
        Optional<Process> processOptional = processDao.findById(processLog.getProcessId());
        Assert.isTrue(processOptional.isPresent(), "流程不存在");
        Process process = processOptional.get();
        Optional<Property> propertyOptional = propertyDao.findById(processLog.getPropertyId());
        Assert.isTrue(propertyOptional.isPresent(), "资产记录不存在");
        Property property = propertyOptional.get();

        // 资产状态检查
        if (step.getStatusRequired() != null
            && step.getStatusRequired() != property.getCurStatus()) {
            return Msg.err("当前资产状态不满足流程要求");
        }

        if (processLog.isPass()) {
            // 最后一个流程
            if (stepOptional.get().getNextStepId() == null) {
                property.setCurStatus(process.getFinalStatus());
                ticket.setCurStatus(TicketStatus.PASS);
            } else {
                property.setCurStatus(PropertyStatus.PROCESSING);
                ticket.setCurStepId(stepOptional.get().getNextStepId());
            }
        } else {
            property.setCurStatus(process.getInitialStatus());
            ticket.setCurStatus(TicketStatus.DENY);
        }

        ticket.setGmtModified(LocalDateTime.now());
        property.setGmtModified(LocalDateTime.now());

        propertyDao.saveAndFlush(property);
        ticketDao.saveAndFlush(ticket);

        processLog.setGmtCreate(LocalDateTime.now());
        processLog.setGmtModified(LocalDateTime.now());
        processLogDao.saveAndFlush(processLog);

        return Msg.ok(null);
    }

    @Autowired
    public ProcessLogServiceImpl(TicketDao ticketDao, ProcessDao processDao, StepDao stepDao,
                                 RoleDao roleDao, PropertyDao propertyDao,
                                 ProcessLogDao processLogDao) {
        this.ticketDao = ticketDao;
        this.processDao = processDao;
        this.stepDao = stepDao;
        this.roleDao = roleDao;
        this.propertyDao = propertyDao;
        this.processLogDao = processLogDao;
    }
}
