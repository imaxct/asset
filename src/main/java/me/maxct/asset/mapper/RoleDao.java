package me.maxct.asset.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.Role;

/**
 * @author imaxct
 * 2019-03-21 13:01
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    @Query("from Role r where r.id in (select ur.roleId from UserRole ur where ur.userId = :userId)")
    List<Role> getUserRoles(@Param("userId") Long userId);
}
