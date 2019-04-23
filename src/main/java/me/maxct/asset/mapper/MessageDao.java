package me.maxct.asset.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Message;

/**
 * @author imaxct
 * 2019-04-23 16:44
 */
@Repository
public interface MessageDao extends JpaRepository<Message, Long> {
}
