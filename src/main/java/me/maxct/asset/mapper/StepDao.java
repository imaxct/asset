package me.maxct.asset.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Step;

/**
 * @author imaxct
 * 2019-03-21 13:03
 */
@Repository
public interface StepDao extends JpaRepository<Step, Long> {
    List<Step> findByProcessId(Long processId);
}
