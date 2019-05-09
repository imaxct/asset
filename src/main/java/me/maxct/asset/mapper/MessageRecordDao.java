package me.maxct.asset.mapper;

import org.springframework.data.jpa.repository.JpaRepository;

import me.maxct.asset.domain.MessageRecord;

/**
 * @author imaxct
 * 2019-05-09 21:23
 */
public interface MessageRecordDao extends JpaRepository<MessageRecord, Long> {
}
