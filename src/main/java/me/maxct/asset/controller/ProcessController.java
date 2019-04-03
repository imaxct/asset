package me.maxct.asset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.ProcessDO;
import me.maxct.asset.interceptor.AuthCheck;
import me.maxct.asset.service.ProcessService;

/**
 * @author imaxct
 * 2019-04-03 18:15
 */
@RestController
@RequestMapping("/Process")
public class ProcessController {
    private final ProcessService processService;

    @AuthCheck
    @PostMapping("/add")
    public Msg addProcess(@RequestBody ProcessDO processDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        return processService.addProcess(null, null);
    }

    @AuthCheck
    @GetMapping("/list")
    public Msg listAll(HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        return processService.getAllProcess();
    }

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }
}
