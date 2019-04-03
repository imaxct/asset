package me.maxct.asset.dto;

import lombok.Data;

/**
 * @author imaxct
 * 2019-04-03 13:55
 */
@Data
public class RoleDO {
    private Long   id;
    private String name;
    private String roleName;
    private String authorizedMapping;
}
