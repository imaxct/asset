package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import me.maxct.asset.domain.Property;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.mapper.PropertyDao;
import me.maxct.asset.service.PropertyService;

/**
 * @author imaxct
 * 2019-03-27 21:13
 */
@Service
public class PropertyServiceImpl implements PropertyService {
    private final PropertyDao propertyDao;

    @Override
    @Transactional
    public Msg addProperty(Property property) {
        property.setGmtCreate(LocalDateTime.now());
        property.setGmtModified(LocalDateTime.now());
        property.setPropertyId(UUID.randomUUID().toString());
        propertyDao.saveAndFlush(property);
        return Msg.ok(property);
    }

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
        return Msg.ok(propertyOptional.get());
    }

    @Autowired
    public PropertyServiceImpl(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }
}
