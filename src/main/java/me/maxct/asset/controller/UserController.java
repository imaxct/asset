package me.maxct.asset.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.LoginDO;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.PageDO;
import me.maxct.asset.dto.UserDO;
import me.maxct.asset.interceptor.AuthCheck;
import me.maxct.asset.service.UserService;

/**
 * @author imaxct
 * 2019-03-22 21:51
 */
@RestController
@RequestMapping("/User")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public Msg login(@RequestBody LoginDO loginDO, HttpServletRequest request) {
        Assert.isTrue(!StringUtils.isEmpty(loginDO.getUsername()), "用户名为空");
        Assert.isTrue(!StringUtils.isEmpty(loginDO.getPassword()), "密码为空");
        return userService.login(loginDO.getUsername(), loginDO.getPassword());
    }

    @AuthCheck
    @PostMapping("/list")
    public Msg listUser(@RequestBody PageDO pageDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        Assert.isTrue(pageDO.getSize() >= 0, "参数错误");

        return userService.listUser(pageDO.getPageNo(), pageDO.getSize());
    }

    @AuthCheck
    @PostMapping("/save")
    public Msg saveUser(@RequestBody UserDO userDO, HttpServletRequest request) {
        User curUser = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(curUser, "鉴权失败");

        Assert.notNull(userDO.getId(), "参数错误");

        try {
            User user = userService.getUser(userDO.getId());

            if (!StringUtils.isEmpty(userDO.getName())) {
                user.setName(userDO.getName());
            }
            if (userDO.getDepId() != null) {
                user.setDepId(userDO.getDepId());
            }
            if (userDO.getRoleId() != null) {
                user.setRoleId(userDO.getRoleId());
            }
            return userService.saveUser(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("用户不存在");
        }
    }

    @AuthCheck
    @PostMapping("/new")
    public Msg createUser(@RequestBody UserDO userDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        Assert.isTrue(!StringUtils.isEmpty(userDO.getUsername()), "用户名为空");
        Assert.isTrue(!StringUtils.isEmpty(userDO.getName()), "姓名为空");
        Assert.isTrue(!StringUtils.isEmpty(userDO.getPassword()), "密码为空");
        Assert.isTrue(6 <= userDO.getPassword().length() && userDO.getPassword().length() <= 64,
            "密码长度错误(6-64)");
        Assert.notNull(userDO.getDepId(), "部门为空");
        Assert.notNull(userDO.getRoleId(), "权限为空");

        User newUser = new User();
        newUser.setName(userDO.getName());
        newUser.setUsername(userDO.getUsername());
        newUser.setPassword(userDO.getPassword());
        newUser.setDepId(userDO.getDepId());
        newUser.setRoleId(userDO.getRoleId());
        newUser.setGmtCreate(LocalDateTime.now());
        return userService.saveUser(newUser);
    }

    @AuthCheck
    @PostMapping("/passwd")
    public Msg changePassword(@RequestBody UserDO userDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        Assert.isTrue(!StringUtils.isEmpty(userDO.getPassword()), "参数错误");
        Assert.isTrue(!StringUtils.isEmpty(userDO.getOldPassword()), "原密码为空");
        Assert.isTrue(6 <= userDO.getPassword().length() && userDO.getPassword().length() <= 64,
            "密码长度错误(6-64)");
        Assert.isTrue(!userDO.getPassword().equals(userDO.getOldPassword()), "新密码与原密码不能相同");

        Assert.isTrue(user.getPassword().equals(userDO.getOldPassword()), "旧密码错误");

        user.setPassword(userDO.getPassword());

        return userService.saveUser(user);
    }

    @AuthCheck
    @GetMapping("/getDepUser")
    public Msg getDepUserList(HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        return userService.getDepUser();
    }

    @AuthCheck
    @GetMapping("/getUserName")
    public Msg getUserNameById(@RequestParam Long id, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        User dbUser = userService.getUser(id);
        return Msg.ok(dbUser.getName());
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

}
