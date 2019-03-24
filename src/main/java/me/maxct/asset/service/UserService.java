package me.maxct.asset.service;

import me.maxct.asset.domain.User;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-03-24 19:11
 */
public interface UserService {
    Msg<User> login(String username, String password);
}
