package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import me.maxct.asset.domain.*;
import me.maxct.asset.domain.Process;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.ProcessLogVO;
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
    private final StepDao       stepDao;
    private final UserDao       userDao;

    @Override
    @Transactional
    public Msg submitTicket(Ticket ticket, User user) {

        Optional<Process> processOptional = processDao.findById(ticket.getProcessId());
        Assert.isTrue(processOptional.isPresent(), "记录不存在");
        Process process = processOptional.get();

        Optional<Property> propertyOptional = propertyDao.findById(ticket.getPropertyId());
        Assert.isTrue(propertyOptional.isPresent(), "记录不存在");
        Property property = propertyOptional.get();

        if (!Objects.equals(property.getDepId(), user.getDepId())) {
            return Msg.err("您没有权限操作其他部门资产");
        }
        ticket.setDepId(user.getDepId());

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
        if (!StringUtils.isEmpty(process.getStatusRequired())) {
            String[] arr = process.getStatusRequired().split(",");
            boolean satisfied = false;
            for (String status : arr) {
                if (property.getCurStatus().getName().equals(status)) {
                    satisfied = true;
                    break;
                }
            }
            if (!satisfied) {
                return Msg.err("当前资产状态不满足, 不能申请当前流程");
            }
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
        ticket.setInitialStatus(property.getCurStatus());
        ticketDao.saveAndFlush(ticket);
        return Msg.ok(Long.toString(ticket.getId()));
    }

    @Override
    public Msg getTicketByUserId(Long applyUserId) {
        List<TicketVO> list = ticketDao.getTicketSimpleList(applyUserId);
        System.out.println(list.size());
        List<Process> processList = processDao.findAll();
        processTicketVOList(list, processList);
        return Msg.ok(list);
    }

    private void processTicketVOList(List<TicketVO> list, List<Process> processList) {
        Map<Long, String> processMap = processList.stream()
            .collect(Collectors.toMap(Process::getId, Process::getName, (o, n) -> n));
        for (TicketVO vo : list) {
            if (processMap.containsKey(vo.getTicket().getProcessId())) {
                vo.setProcessName(processMap.get(vo.getTicket().getProcessId()));
            }
        }
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
        List<ProcessLogVO> logList = new ArrayList<>();
        Optional<User> userOptional = userDao.findById(ticket.getApplyUserId());
        Assert.isTrue(userOptional.isPresent(), "记录不存在");

        logList.add(new ProcessLogVO(ticket.getGmtCreate(), "提交流程", userOptional.get().getName(),
            true, ticket.getApplyReason()));
        logList.addAll(processLogDao.getTicketLogs(ticketId));

        ticketVO.setTicket(ticket);
        ticketVO.setLogs(logList);
        ticketVO.setPropertyId(property.getPropertyId());
        ticketVO.setPropertyName(property.getName());

        return Msg.ok(ticketVO);
    }

    @Override
    public Msg getTodoList(User user) {
        List<TicketVO> list = ticketDao.getDepTicketList(user.getDepId());
        List<Process> processList = processDao.findAll();

        Optional<Role> roleOptional = roleDao.findById(user.getRoleId());
        Assert.isTrue(roleOptional.isPresent(), "权限错误");
        Role role = roleOptional.get();

        Iterator<TicketVO> it = list.iterator();
        while (it.hasNext()) {
            TicketVO vo = it.next();
            if (!StringUtils.isEmpty(vo.getCurStep().getRoleRequired())) {
                String[] arr = vo.getCurStep().getRoleRequired().split(",");
                boolean match = false;
                for (String auth : arr) {
                    if (role.getRoleName().equals(auth)) {
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    it.remove();
                }
            }
        }
        processTicketVOList(list, processList);

        return Msg.ok(list);
    }

    @Autowired
    public TicketServiceImpl(TicketDao ticketDao, ProcessDao processDao, PropertyDao propertyDao,
                             ProcessLogDao processLogDao, RoleDao roleDao, StepDao stepDao,
                             UserDao userDao) {
        this.ticketDao = ticketDao;
        this.processDao = processDao;
        this.propertyDao = propertyDao;
        this.processLogDao = processLogDao;
        this.roleDao = roleDao;
        this.stepDao = stepDao;
        this.userDao = userDao;
    }
}
