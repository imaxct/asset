package me.maxct.asset.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.maxct.asset.interceptor.AuthCheck;

/**
 * @author imaxct
 * 2019-03-22 21:51
 */
@RestController
public class CommonController {

    @AuthCheck
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
