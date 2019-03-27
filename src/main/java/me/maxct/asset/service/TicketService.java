package me.maxct.asset.service;

import me.maxct.asset.domain.Ticket;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-03-24 21:06
 */
public interface TicketService {

    /**
     * 提交工单
     * @param ticket
     * @return
     */
    Msg submitTicket(Ticket ticket);

    /**
     * 获取用户的工单
     * @param applyUserId
     * @return
     */
    Msg getTicketByUserId(Long applyUserId);

    /**
     * 获取工单详情 处理记录(步骤定义从processService获取)
     * @param ticketId
     * @return
     */
    Msg getTicketDetail(Long ticketId);
}
