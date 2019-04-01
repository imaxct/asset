package me.maxct.asset.dto;

import lombok.Data;
import me.maxct.asset.domain.Role;

/**
 * @author imaxct
 * 2019-04-01 13:46
 */
@Data
public class RoleVO {
    private String name;
    private String roleName;

    public RoleVO(Role role) {
        this.name = role.getName();
        this.roleName = role.getRoleName();
    }
}
