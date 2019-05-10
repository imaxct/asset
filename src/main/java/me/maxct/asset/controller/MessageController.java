package me.maxct.asset.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.Message;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.MessageDO;
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

    @AuthCheck
    @GetMapping("/unread")
    public Msg getUnread(HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        return messageService.getUnreadMessage(user.getId());
    }

    @AuthCheck
    @PostMapping("/add")
    public Msg addMsg(@RequestBody MessageDO messageDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        Assert.isTrue(!StringUtils.isEmpty(messageDO.getTitle()), "标题不能为空.");
        Assert.isTrue(!StringUtils.isEmpty(messageDO.getContent()), "内容不能为空.");
        Message message = new Message();
        message.setGmtCreate(LocalDateTime.now());
        message.setTitle(messageDO.getTitle());
        message.setContent(messageDO.getContent());
        return messageService.saveMessage(message);
    }

    @AuthCheck
    @PostMapping("/del")
    public Msg deleteMsg(@RequestBody MessageDO messageDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        Assert.notNull(messageDO.getId(), "id为空");
        return messageService.deleteMessage(messageDO.getId());
    }

    @AuthCheck
    @PostMapping("/update")
    public Msg updateMsg(@RequestBody MessageDO messageDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        Assert.notNull(messageDO.getId(), "id不能为空");
        Assert.isTrue(!StringUtils.isEmpty(messageDO.getTitle()), "标题不能为空");
        Assert.isTrue(!StringUtils.isEmpty(messageDO.getContent()), "内容不能为空");
        Message message = new Message();
        message.setId(messageDO.getId());
        message.setTitle(messageDO.getTitle());
        message.setContent(messageDO.getContent());
        return messageService.saveMessage(message);
    }

    @AuthCheck
    @PostMapping("/read")
    public Msg readMsg(@RequestBody MessageDO messageDO, HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        Assert.notNull(messageDO.getId(), "id不能为空");
        return messageService.readMessage(user.getId(), messageDO.getId());
    }

    @AuthCheck
    @GetMapping("/count")
    public Msg countUnreadMsg(HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        return messageService.countUnread(user.getId());
    }

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
}
