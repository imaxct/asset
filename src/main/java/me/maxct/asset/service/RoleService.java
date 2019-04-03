package me.maxct.asset.service;

import me.maxct.asset.domain.Role;
import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-04-01 16:53
 */
public interface RoleService {
    Msg listRole();

    Msg saveRole(Role role);

    Msg deleteRole(Long id);
}
