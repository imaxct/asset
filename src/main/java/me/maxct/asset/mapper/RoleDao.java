package me.maxct.asset.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Role;

/**
 * @author imaxct
 * 2019-03-21 13:01
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
}
