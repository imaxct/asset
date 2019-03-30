package me.maxct.asset.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Ticket;
import me.maxct.asset.enumerate.PropertyStatus;

/**
 * @author imaxct
 * 2019-03-21 13:04
 */
@Repository
public interface TicketDao extends JpaRepository<Ticket, Long> {
    List<Ticket> getByApplyUserId(Long applyUserId);

    Optional<Ticket> findByPropertyIdAndCurStatus(Long propertyId, PropertyStatus curStatus);
}
