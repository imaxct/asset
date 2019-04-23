package me.maxct.asset.service.impl;

import org.springframework.stereotype.Service;

import me.maxct.asset.domain.Message;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.mapper.MessageDao;
import me.maxct.asset.service.MessageService;

/**
 * @author imaxct
 * 2019-04-23 16:49
 */
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageDao messageDao;

    @Override
    public Msg getAllMessage() {
        return null;
    }

    @Override
    public Msg getUnreadMessage(Long userId) {
        return null;
    }

    @Override
    public Msg saveMessage(Message message) {
        return null;
    }

    @Override
    public Msg deleteMessage(Long id) {
        return null;
    }

    @Override
    public Msg readMessage(Long userId, Long msgId) {
        return null;
    }

    public MessageServiceImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
}
