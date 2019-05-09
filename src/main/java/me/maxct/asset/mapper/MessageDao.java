package me.maxct.asset.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Message;

/**
 * @author imaxct
 * 2019-04-23 16:44
 */
@Repository
public interface MessageDao extends JpaRepository<Message, Long> {
    @Query("from Message m where not exists (select mr.id from MessageRecord mr where mr.userId = :id)")
    List<Message> getUnreadMessage(@Param("id") Long userId);
}
