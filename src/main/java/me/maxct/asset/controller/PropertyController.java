package me.maxct.asset.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.Property;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.PropertyDO;
import me.maxct.asset.interceptor.AuthCheck;
import me.maxct.asset.service.PropertyService;

/**
 * @author imaxct
 * 2019-03-30 13:49
 */
@RestController
@RequestMapping("/Prop")
public class PropertyController {
    private final PropertyService propertyService;

    @AuthCheck
    @GetMapping("/get")
    public Msg getPropertyDetail(@RequestParam("id") String propertyId,
                                 HttpServletRequest request) {

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        return propertyService.getPropertyById(propertyId);
    }

    @AuthCheck
    @PostMapping("/add")
    public Msg addProperty(@RequestBody List<PropertyDO> properties, HttpServletRequest request) {
        Assert.notEmpty(properties, "参数错误");

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        List<Property> list = new ArrayList<>();
        for (PropertyDO propertyDO : properties) {
            Property property = new Property();
            property.setDepId(propertyDO.getDepId());
            property.setName(propertyDO.getName());
            list.add(property);
        }

        return propertyService.addProperty(list);
    }

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
}
