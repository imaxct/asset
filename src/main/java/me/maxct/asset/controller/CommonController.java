package me.maxct.asset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.security.JwtUtil;
import me.maxct.asset.service.UserService;

/**
 * @author imaxct
 * 2019-03-22 21:51
 */
@RestController
@RequestMapping("/Common")
public class CommonController {

    private final UserService userService;

    private final JwtUtil     jwtUtil;

    @Autowired
    public CommonController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/login")
    public Msg login(@RequestParam String username, @RequestParam String password,
                     HttpServletRequest request) {
        Msg<User> msg = userService.login(username, password);
        if (msg.isOk() && null != msg.getObj()) {
            return Msg.ok(jwtUtil.sign(msg.getObj()));
        } else {
            return Msg.err(msg.getMsg());
        }
    }

}
