package me.maxct.asset.service;

import me.maxct.asset.domain.Message;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-04-23 16:45
 */
public interface MessageService {
    Msg getAllMessage();

    Msg getUnreadMessage(Long userId);

    Msg saveMessage(Message message);

    Msg deleteMessage(Long id);

    Msg readMessage(Long userId, Long msgId);

    Msg countUnread(Long userId);
}
