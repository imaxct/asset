package me.maxct.asset.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.maxct.asset.domain.User;

/**
 * @author imaxct
 * 2019-03-21 11:28
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User getUserByUsername(String username);
}
