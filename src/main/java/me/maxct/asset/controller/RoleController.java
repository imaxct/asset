package me.maxct.asset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.Role;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.RoleDO;
import me.maxct.asset.interceptor.AuthCheck;
import me.maxct.asset.service.RoleService;

/**
 * @author imaxct
 * 2019-04-01 15:48
 */
@RestController
@RequestMapping("/Role")
public class RoleController {
    private final RoleService roleService;

    @AuthCheck
    @GetMapping("/list")
    public Msg listRoles(HttpServletRequest request) {

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        return roleService.listRole();
    }

    @AuthCheck
    @PostMapping("/save")
    public Msg saveRole(@RequestBody RoleDO roleDO, HttpServletRequest request) {

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        Role role = new Role();
        role.setName(roleDO.getName());
        role.setRoleName(roleDO.getRoleName());
        role.setAuthorizedMapping(roleDO.getAuthorizedMapping());
        if (roleDO.getId() != null) {
            role.setId(roleDO.getId());
        }
        return roleService.saveRole(role);
    }

    @AuthCheck
    @PostMapping("/delete")
    public Msg deleteRole(@RequestBody RoleDO roleDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        Assert.notNull(roleDO, "参数不能为空");
        Assert.notNull(roleDO.getId(), "id不能为空");
        return roleService.deleteRole(roleDO.getId());
    }

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
}
