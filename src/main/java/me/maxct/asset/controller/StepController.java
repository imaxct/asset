package me.maxct.asset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.Step;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.StepDO;
import me.maxct.asset.interceptor.AuthCheck;
import me.maxct.asset.service.StepService;

/**
 * @author imaxct
 * 2019-05-14 18:43
 */
@RestController
@RequestMapping("/Step")
public class StepController {
    private final StepService stepService;

    @AuthCheck
    @PostMapping("/update")
    public Msg updateStep(@RequestBody StepDO stepDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        Assert.notNull(stepDO.getId(), "参数错误");
        if (stepDO.getName() == null && StringUtils.isEmpty(stepDO.getRoleRequired())
            && stepDO.getStatusRequired() == null) {
            return Msg.err("参数错误");
        }
        Step step = new Step();
        step.setName(stepDO.getName());
        step.setId(stepDO.getId());
        step.setStatusRequired(stepDO.getStatusRequired());
        step.setRoleRequired(stepDO.getRoleRequired());
        return stepService.updateStep(step);
    }

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }
}
