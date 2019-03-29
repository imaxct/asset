package me.maxct.asset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.ProcessLog;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.ProcessLogDO;
import me.maxct.asset.interceptor.AuthCheck;
import me.maxct.asset.service.ProcessLogService;

/**
 * @author imaxct
 * 2019-03-29 10:54
 */
@RestController
@RequestMapping("/Log")
public class ProcessLogController {
    private final ProcessLogService processLogService;

    @AuthCheck
    @PostMapping("/process")
    public Msg process(@RequestBody ProcessLogDO processLogDO, HttpServletRequest request) {
        Assert.notNull(processLogDO, "参数错误");
        Assert.notNull(processLogDO.getProcessProposal(), "审批意见不能为空");
        Assert.notNull(processLogDO.getStepId(), "未选择步骤");
        Assert.notNull(processLogDO.getTicketId(), "未选择工单");

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        ProcessLog processLog = new ProcessLog();
        processLog.setPass(processLogDO.isPass());
        processLog.setProcessProposal(processLogDO.getProcessProposal());
        processLog.setProcessUserId(user.getId());
        processLog.setStepId(processLogDO.getStepId());

        return processLogService.processStep(processLog);
    }

    public ProcessLogController(ProcessLogService processLogService) {
        this.processLogService = processLogService;
    }
}
