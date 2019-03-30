package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import me.maxct.asset.domain.Property;
import me.maxct.asset.domain.Step;
import me.maxct.asset.domain.Ticket;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.PropertyVO;
import me.maxct.asset.enumerate.PropertyStatus;
import me.maxct.asset.mapper.ProcessDao;
import me.maxct.asset.mapper.PropertyDao;
import me.maxct.asset.mapper.StepDao;
import me.maxct.asset.mapper.TicketDao;
import me.maxct.asset.service.PropertyService;

/**
 * @author imaxct
 * 2019-03-27 21:13
 */
@Service
public class PropertyServiceImpl implements PropertyService {
    private final PropertyDao propertyDao;
    private final ProcessDao  processDao;
    private final TicketDao   ticketDao;
    private final StepDao     stepDao;

    @Override
    public Msg getPropertyById(Long id) {
        Optional<Property> propertyOptional = propertyDao.findById(id);
        Assert.isTrue(propertyOptional.isPresent(), "记录不存在");
        return Msg.ok(propertyOptional.get());
    }

    @Override
    public Msg getPropertyById(String propertyId) {
        Optional<Property> propertyOptional = propertyDao.findByPropertyId(propertyId);
        Assert.isTrue(propertyOptional.isPresent(), "记录不存在");
        Property property = propertyOptional.get();

        Optional<Ticket> ticketOptional = ticketDao.findByPropertyIdAndCurStatus(property.getId(),
            PropertyStatus.PROCESSING);
        Ticket ticket = null;
        Step step = null;
        if (ticketOptional.isPresent()) {
            Optional<Step> stepOptional = stepDao.findById(ticketOptional.get().getCurStepId());
            if (stepOptional.isPresent()) {
                step = stepOptional.get();
            }
            ticket = ticketOptional.get();
        }

        PropertyVO propertyVO = new PropertyVO();
        propertyVO.setProperty(property);
        propertyVO.setProcesses(processDao.findByInitialStatus(property.getCurStatus()));
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

    @Autowired
    public PropertyServiceImpl(PropertyDao propertyDao, ProcessDao processDao, StepDao stepDao,
                               TicketDao ticketDao) {
        this.propertyDao = propertyDao;
        this.processDao = processDao;
        this.stepDao = stepDao;
        this.ticketDao = ticketDao;
    }
}
