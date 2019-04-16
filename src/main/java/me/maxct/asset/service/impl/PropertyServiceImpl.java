package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import me.maxct.asset.domain.*;
import me.maxct.asset.domain.Process;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.PropertySimpleVO;
import me.maxct.asset.dto.PropertyVO;
import me.maxct.asset.enumerate.PropertyStatus;
import me.maxct.asset.enumerate.TicketStatus;
import me.maxct.asset.mapper.*;
import me.maxct.asset.service.PropertyService;

/**
 * @author imaxct
 * 2019-03-27 21:13
 */
@Service
public class PropertyServiceImpl implements PropertyService {
    private final PropertyDao   propertyDao;
    private final ProcessDao    processDao;
    private final TicketDao     ticketDao;
    private final StepDao       stepDao;
    private final DepartmentDao departmentDao;

    @Override
    public Msg getPropertyById(Long id) {
        Optional<Property> propertyOptional = propertyDao.findById(id);
        Assert.isTrue(propertyOptional.isPresent(), "记录不存在");
        Map<Long, String> processName = getProcessNameMap();
        Map<Long, String> depName = getDepNameMap();
        return Msg.ok(convertToSimpleVOList(Collections.singletonList(propertyOptional.get()),
            processName, depName));
    }

    @Override
    public Msg getPropertyById(String propertyId) {
        Optional<Property> propertyOptional = propertyDao.findByPropertyId(propertyId);
        Assert.isTrue(propertyOptional.isPresent(), "记录不存在");
        Property property = propertyOptional.get();

        Optional<Ticket> ticketOptional = ticketDao.findByPropertyIdAndCurStatus(property.getId(),
            TicketStatus.PROCESSING);
        Ticket ticket = null;
        Step step = null;
        if (ticketOptional.isPresent()) {
            Optional<Step> stepOptional = stepDao.findById(ticketOptional.get().getCurStepId());
            if (stepOptional.isPresent()) {
                step = stepOptional.get();
            }
            ticket = ticketOptional.get();
        }

        Map<Long, String> processName = getProcessNameMap();
        Map<Long, String> depName = getDepNameMap();
        PropertyVO propertyVO = new PropertyVO();
        propertyVO.setProperty(convertToSimpleVO(property, processName, depName));
        // propertyVO.setProcesses(processDao.findByInitialStatus(property.getCurStatus()));
        propertyVO.setTicket(ticket);
        propertyVO.setStep(step);
        return Msg.ok(propertyVO);
    }

    @Override
    @Transactional
    public Msg addProperty(List<Property> properties) {
        for (Property property : properties) {
            property.setGmtCreate(LocalDateTime.now());
            property.setGmtModified(LocalDateTime.now());
            property.setPropertyId(UUID.randomUUID().toString());
            property.setCurStatus(PropertyStatus.PENDING_IMPORT);
        }
        propertyDao.saveAll(properties);
        return Msg.ok(properties);
    }

    @Override
    public Msg getByUserId(Long userId) {
        List<Property> list = propertyDao.findByOccupyUserId(userId);
        Map<Long, String> processName = getProcessNameMap();
        Map<Long, String> depName = getDepNameMap();
        return Msg.ok(convertToSimpleVOList(list, processName, depName));
    }

    @Override
    public Msg getByDepId(Long depId) {
        List<Property> list = propertyDao.findByDepId(depId);
        Map<Long, String> processName = getProcessNameMap();
        Map<Long, String> depName = getDepNameMap();
        return Msg.ok(convertToSimpleVOList(list, processName, depName));
    }

    private List<PropertySimpleVO> convertToSimpleVOList(List<Property> properties,
                                                         Map<Long, String> processName,
                                                         Map<Long, String> depName) {
        List<PropertySimpleVO> result = new ArrayList<>(properties.size());
        for (Property property : properties) {
            result.add(convertToSimpleVO(property, processName, depName));
        }
        return result;
    }

    private PropertySimpleVO convertToSimpleVO(Property property, Map<Long, String> processName,
                                               Map<Long, String> depName) {
        PropertySimpleVO vo = new PropertySimpleVO();
        vo.setId(property.getId());
        vo.setName(property.getName());
        vo.setCurStatus(property.getCurStatus());
        if (property.getProcessId() != null && processName.containsKey(property.getProcessId())) {
            vo.setCurProcess(processName.get(property.getProcessId()));
        } else {
            vo.setCurProcess("无");
        }
        if (property.getDepId() != null && depName.containsKey(property.getDepId())) {
            vo.setDepName(depName.get(property.getDepId()));
        }
        vo.setOccupyUserId(property.getOccupyUserId());
        vo.setPropertyId(property.getPropertyId());
        vo.setGmtCreate(property.getGmtCreate());
        vo.setGmtModified(property.getGmtModified());
        return vo;
    }

    @Override
    public Msg list() {
        return Msg.ok(propertyDao.findAll());
    }

    private Map<Long, String> getProcessNameMap() {
        List<Process> processList = processDao.findAll();
        return processList.stream()
            .collect(Collectors.toMap(Process::getId, Process::getName, (o, n) -> n));
    }

    private Map<Long, String> getDepNameMap() {
        List<Department> departments = departmentDao.findAll();
        return departments.stream()
            .collect(Collectors.toMap(Department::getId, Department::getName, (o, n) -> n));
    }

    @Autowired
    public PropertyServiceImpl(PropertyDao propertyDao, ProcessDao processDao, StepDao stepDao,
                               TicketDao ticketDao, DepartmentDao departmentDao) {
        this.propertyDao = propertyDao;
        this.processDao = processDao;
        this.stepDao = stepDao;
        this.ticketDao = ticketDao;
        this.departmentDao = departmentDao;
    }
}
