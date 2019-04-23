package me.maxct.asset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.interceptor.AuthCheck;
import me.maxct.asset.service.MessageService;

/**
 * @author imaxct
 * 2019-04-23 16:50
 */
@RestController
@RequestMapping("/Msg")
public class MessageController {
    private final MessageService messageService;

    @AuthCheck
    @GetMapping("/list")
    public Msg listMsg(HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        return messageService.getAllMessage();
    }

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
}
