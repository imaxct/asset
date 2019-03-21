package me.maxct.asset.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Ticket;

/**
 * @author imaxct
 * 2019-03-21 13:04
 */
@Repository
public interface TicketDao extends JpaRepository<Ticket, Long> {
}
