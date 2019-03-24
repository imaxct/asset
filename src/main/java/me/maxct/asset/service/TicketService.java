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

    Msg getTicketByUserId(String applyUserId);


}
