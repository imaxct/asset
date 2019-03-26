package me.maxct.asset.service.impl;

import org.springframework.stereotype.Service;

import me.maxct.asset.domain.Ticket;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.service.TicketService;

/**
 * @author imaxct
 * 2019-03-26 21:04
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Override
    public Msg submitTicket(Ticket ticket) {
        // 提交工单时，需要校验Process的initialStatus与Property的当前状态是否一致
        return null;
    }

    @Override
    public Msg getTicketByUserId(String applyUserId) {
        return null;
    }
}
