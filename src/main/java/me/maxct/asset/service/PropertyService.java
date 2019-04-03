package me.maxct.asset.service;

import java.util.List;

import me.maxct.asset.domain.Property;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-03-25 16:42
 */
public interface PropertyService {

    /**
     * 根据id获取
     * @param id
     * @return
     */
    Msg getPropertyById(Long id);

    /**
     * 根据uid获取
     * @param propertyId
     * @return
     */
    Msg getPropertyById(String propertyId);

    /**
     * 批量添加
     * @param properties
     * @return
     */
    Msg addProperty(List<Property> properties);

    /**
     * 获取可操作的
     * @param userId
     * @param depId
     * @return
     */
    Msg getAvailable(Long userId, Long depId);

    Msg list();

}
