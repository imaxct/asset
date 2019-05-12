package me.maxct.asset.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import me.maxct.asset.domain.Message;
import me.maxct.asset.domain.MessageRecord;
import me.maxct.asset.dto.Msg;
import me.maxct.asset.mapper.MessageDao;
import me.maxct.asset.mapper.MessageRecordDao;
import me.maxct.asset.service.MessageService;

/**
 * @author imaxct
 * 2019-04-23 16:49
 */
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageDao          messageDao;
    private final MessageRecordDao    messageRecordDao;
    private final TransactionTemplate template;
    private static final Logger       LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public Msg getAllMessage() {
        return Msg.ok(messageDao.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public Msg getUnreadMessage(Long userId) {
        return Msg.ok(messageDao.getUnreadMessage(userId));
    }

    @Override
    public Msg saveMessage(Message message) {
        Optional<Message> messageOptional = messageDao.findById(message.getId());
        Assert.isTrue(messageOptional.isPresent(), "参数错误");
        Message dbMessage = messageOptional.get();
        if (!StringUtils.isEmpty(message.getTitle())) {
            dbMessage.setTitle(message.getTitle());
        }
        if (!StringUtils.isEmpty(message.getContent())) {
            dbMessage.setContent(message.getContent());
        }
        message.setGmtModified(LocalDateTime.now());
        return Msg.ok(messageDao.saveAndFlush(dbMessage));
    }

    @Override
    @Transactional
    public Msg deleteMessage(Long id) {
        messageDao.deleteById(id);
        return Msg.ok(null);
    }

    @Override
    public Msg readMessage(Long userId, Long msgId) {
        MessageRecord record = new MessageRecord();
        record.setGmtCreate(LocalDateTime.now());
        record.setGmtModified(LocalDateTime.now());
        record.setUserId(userId);
        record.setMsgId(msgId);

        return template.execute(t -> {
            try {
                return Msg.ok(messageRecordDao.saveAndFlush(record));
            } catch (Exception e) {
                LOGGER.error("readMessage error", e);
                t.setRollbackOnly();
            }
            return Msg.err("保存失败");
        });
    }

    @Override
    public Msg countUnread(Long userId) {
        long readCount = messageRecordDao.countByUserId(userId);
        long count = messageDao.count();
        return Msg.ok(count - readCount);
    }

    @Override
    public Msg pageList(int pageNo, int size) {
        return Msg.ok(messageDao.findAll(PageRequest.of(pageNo, size)));
    }

    @Override
    public Msg createMessage(Message message) {
        message.setGmtCreate(LocalDateTime.now());
        message.setGmtModified(LocalDateTime.now());
        return Msg.ok(messageDao.saveAndFlush(message));
    }

    public MessageServiceImpl(MessageDao messageDao, TransactionTemplate template,
                              MessageRecordDao messageRecordDao) {
        this.messageDao = messageDao;
        this.template = template;
        this.messageRecordDao = messageRecordDao;
    }
}
