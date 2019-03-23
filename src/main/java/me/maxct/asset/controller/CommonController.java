package me.maxct.asset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.maxct.asset.dto.Msg;
import me.maxct.asset.interceptor.AuthCheck;

/**
 * @author imaxct
 * 2019-03-22 21:51
 */
@RestController
public class CommonController {

    @AuthCheck
    @GetMapping("/test")
    public Msg test(HttpServletRequest request) {
        return Msg.ok("null", null);
    }
}
