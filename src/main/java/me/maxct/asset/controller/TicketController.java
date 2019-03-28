package me.maxct.asset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.maxct.asset.constant.AppConst;
import me.maxct.asset.domain.Ticket;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.dto.TicketDO;
import me.maxct.asset.interceptor.AuthCheck;
import me.maxct.asset.service.TicketService;

/**
 * @author imaxct
 * 2019-03-28 22:37
 */
@RestController
@RequestMapping("/Ticket")
public class TicketController {
    private final TicketService ticketService;

    @AuthCheck
    @PostMapping("/submit")
    public Msg submitTicket(@RequestBody TicketDO ticketDO, HttpServletRequest request) {
        Assert.notNull(ticketDO, "参数错误");
        Assert.notNull(ticketDO.getApplyReason(), "申请原因不能为空");
        Assert.notNull(ticketDO.getProcessId(), "未选择流程");
        Assert.notNull(ticketDO.getPropertyId(), "未选择资产");
        Assert.notNull(ticketDO.getUserId(), "用户标识为空");

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        Assert.isTrue(user.getId().compareTo(ticketDO.getUserId()) == 0, "申请用户与当前用户不一致");

        Ticket ticket = new Ticket();
        ticket.setApplyReason(ticketDO.getApplyReason());
        ticket.setApplyUserId(ticketDO.getUserId());
        ticket.setPropertyId(ticketDO.getPropertyId());
        ticket.setProcessId(ticketDO.getProcessId());

        return ticketService.submitTicket(ticket);
    }

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}
