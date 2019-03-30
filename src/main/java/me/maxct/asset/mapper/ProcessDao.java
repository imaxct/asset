package me.maxct.asset.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Process;
import me.maxct.asset.enumerate.PropertyStatus;

/**
 * @author imaxct
 * 2019-03-21 13:02
 */
@Repository
public interface ProcessDao extends JpaRepository<Process, Long> {
    List<Process> findByInitialStatus(PropertyStatus initialStatus);
}
