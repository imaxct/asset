package me.maxct.asset.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.Process;
import me.maxct.asset.domain.Step;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.ProcessDO;
import me.maxct.asset.dto.StepDO;
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
        Assert.isTrue(!StringUtils.isEmpty(processDO.getName()), "流程名为空");

        Process process = new Process();
        process.setInitialStatus(processDO.getInitialStatus());
        process.setFinalStatus(processDO.getFinalStatus());
        process.setTransferType(processDO.getTransferType());
        process.setName(processDO.getName());

        List<Step> steps = new ArrayList<>();
        for (StepDO stepDO : processDO.getStep()) {
            Assert.isTrue(!StringUtils.isEmpty(stepDO.getName()), "步骤名为空");
            Assert.isTrue(!StringUtils.isEmpty(stepDO.getRoleRequired()), "需要权限为空");
            Step step = new Step();
            step.setName(stepDO.getName());
            step.setStatusRequired(stepDO.getStatusRequired());
            step.setRoleRequired(stepDO.getRoleRequired());
            steps.add(step);
        }

        return processService.addProcess(process, steps);
    }

    @AuthCheck
    @GetMapping("/list")
    public Msg listAll(HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        return processService.getAllProcess();
    }

    @AuthCheck
    @GetMapping("/get")
    public Msg getById(@RequestParam Long id, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        return processService.getById(id);
    }

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }
}
