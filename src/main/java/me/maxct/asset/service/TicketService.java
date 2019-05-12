package me.maxct.asset.service;

import me.maxct.asset.domain.Ticket;
import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-03-24 21:06
 */
public interface TicketService {

    /**
     * 提交工单
     * @param ticket 工单
     * @param user 用户
     * @return return
     */
    Msg submitTicket(Ticket ticket, User user);

    /**
     * 获取用户的工单
     * @param applyUserId 用户id
     * @return return
     */
    Msg getTicketByUserId(Long applyUserId);

    /**
     * 获取工单详情 处理记录(步骤定义从processService获取)
     * @param ticketId 工单id
     * @return return
     */
    Msg getTicketDetail(Long ticketId);

    /**
     * 获取待审核工单列表
     * @param user 用户
     * @return return
     */
    Msg getTodoList(User user);

    /**
     * 通过流程获取工单记录
     * @param processId id
     * @return msg
     */
    Msg getByProcess(Long processId, int pageNo, int size);
}
