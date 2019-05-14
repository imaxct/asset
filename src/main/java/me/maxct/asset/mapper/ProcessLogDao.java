package me.maxct.asset.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.ProcessLog;
import me.maxct.asset.dto.ProcessLogVO;

/**
 * @author imaxct
 * 2019-03-21 13:02
 */
@Repository
public interface ProcessLogDao extends JpaRepository<ProcessLog, Long> {
    List<ProcessLog> findByTicketId(Long ticketId);

    @Query("select new me.maxct.asset.dto.ProcessLogVO(p.gmtCreate, s.name, u.name, p.pass, p.processProposal, s.id) "
           + "from ProcessLog p ,Step s, User u where p.ticketId = :id and p.stepId = s.id and p.processUserId = u.id")
    List<ProcessLogVO> getTicketLogs(@Param("id") Long ticketId);
}
