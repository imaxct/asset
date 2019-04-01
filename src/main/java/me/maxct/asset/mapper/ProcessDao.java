package me.maxct.asset.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Process;
import me.maxct.asset.enumerate.PropertyStatus;

/**
 * @author imaxct
 * 2019-03-21 13:02
 */
@Repository
public interface ProcessDao extends JpaRepository<Process, Long> {
    @Query("from Process p where p.initialStatus = :status or p.initialStatus is null")
    List<Process> findByInitialStatus(@Param("status") PropertyStatus initialStatus);
}
