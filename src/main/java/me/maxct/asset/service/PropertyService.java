package me.maxct.asset.service;

import me.maxct.asset.domain.Property;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-03-25 16:42
 */
public interface PropertyService {

    Msg addProperty(Property property);

    Msg getPropertyById(Long id);

    Msg getPropertyById(String propertyId);

}
