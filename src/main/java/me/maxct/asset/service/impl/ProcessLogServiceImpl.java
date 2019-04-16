package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
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
    private final UserDao       userDao;

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
        if (step.getId().compareTo(ticket.getCurStepId()) != 0) {
            return Msg.err("流程不正确");
        }

        // 流程发起人不能进行操作
        if (ticket.getApplyUserId().compareTo(processLog.getProcessUserId()) == 0) {
            return Msg.err("流程发起人不能进行操作");
        }

        // 当前step权限检查
        Optional<User> userOptional = userDao.findById(processLog.getProcessUserId());
        Assert.isTrue(userOptional.isPresent(), "参数错误");

        Optional<Role> roleOptional = roleDao.findById(userOptional.get().getRoleId());
        Assert.isTrue(roleOptional.isPresent(), "没有权限进行当前操作");

        if (!step.getRoleRequired().contains(roleOptional.get().getRoleName())) {
            return Msg.err("您没有权限进行当前操作");
        }
        Optional<Process> processOptional = processDao.findById(ticket.getProcessId());
        Assert.isTrue(processOptional.isPresent(), "流程不存在");
        Process process = processOptional.get();
        Optional<Property> propertyOptional = propertyDao.findById(ticket.getPropertyId());
        Assert.isTrue(propertyOptional.isPresent(), "资产记录不存在");
        Property property = propertyOptional.get();

        if (processLog.isPass()) {
            // 最后一个流程
            if (step.getNextStepId() == null) {
                if (process.getFinalStatus() != null) {
                    property.setCurStatus(process.getFinalStatus());
                }
                if (process.getTransferType() != null) {
                    switch (process.getTransferType()) {
                        case APPLY_USER:
                            property.setOccupyUserId(ticket.getApplyUserId());
                            break;
                        case STEP_HANDLER:
                            property.setOccupyUserId(processLog.getProcessUserId());
                            break;
                        case SPECIFIC_USER:
                            property.setOccupyUserId(ticket.getTransferUserId());
                            break;
                        default:
                            property.setOccupyUserId(null);
                    }
                }
                ticket.setCurStatus(TicketStatus.PASS);
                ticket.setCurStepId(null);
                property.setProcessId(null);
            } else {
                property.setCurStatus(PropertyStatus.PROCESSING);
                ticket.setCurStepId(stepOptional.get().getNextStepId());
            }
        } else {
            property.setCurStatus(ticket.getInitialStatus());
            ticket.setCurStatus(TicketStatus.DENY);
            property.setProcessId(null);
        }

        ticket.setGmtModified(LocalDateTime.now());
        property.setGmtModified(LocalDateTime.now());

        propertyDao.saveAndFlush(property);
        ticketDao.saveAndFlush(ticket);

        processLog.setPropertyId(ticket.getPropertyId());
        processLog.setProcessId(ticket.getProcessId());
        processLog.setGmtCreate(LocalDateTime.now());
        processLog.setGmtModified(LocalDateTime.now());
        processLogDao.saveAndFlush(processLog);

        return Msg.ok(null);
    }

    @Autowired
    public ProcessLogServiceImpl(TicketDao ticketDao, ProcessDao processDao, StepDao stepDao,
                                 RoleDao roleDao, PropertyDao propertyDao,
                                 ProcessLogDao processLogDao, UserDao userDao) {
        this.ticketDao = ticketDao;
        this.processDao = processDao;
        this.stepDao = stepDao;
        this.roleDao = roleDao;
        this.propertyDao = propertyDao;
        this.processLogDao = processLogDao;
        this.userDao = userDao;
    }
}
