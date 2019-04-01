package me.maxct.asset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.maxct.asset.dto.Msg;
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
    public Msg login(@RequestParam String username, @RequestParam String password,
                     HttpServletRequest request) {
        return userService.login(username, password);
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

}
