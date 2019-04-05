package me.maxct.asset.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        Ticket ticket = new Ticket();
        ticket.setApplyReason(ticketDO.getApplyReason());
        ticket.setApplyUserId(user.getId());
        ticket.setPropertyId(ticketDO.getPropertyId());
        ticket.setProcessId(ticketDO.getProcessId());
        ticket.setTransferUserId(ticketDO.getTransferUserId());
        if (ticketDO.getFinalStatus() != null) {
            ticket.setFinalStatus(ticketDO.getFinalStatus());
        }

        return ticketService.submitTicket(ticket);
    }

    @AuthCheck
    @GetMapping("/get")
    public Msg getTicketDetail(@RequestParam("id") Long ticketId, HttpServletRequest request) {
        Assert.notNull(ticketId, "参数错误");

        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");

        return ticketService.getTicketDetail(ticketId);
    }

    @AuthCheck
    @GetMapping("/list")
    public Msg getUserTickets(HttpServletRequest request) {
        User user = (User) request.getAttribute(AppConst.USER_KEY);
        Assert.notNull(user, "鉴权失败");
        return ticketService.getTicketByUserId(user.getId());
    }

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}
