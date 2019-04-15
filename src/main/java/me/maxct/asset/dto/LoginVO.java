package me.maxct.asset.dto;

import lombok.Data;

/**
 * @author imaxct
 * 2019-03-28 19:41
 */
@Data
public class LoginVO {
    private Long   id;
    private String username;
    private String name;
    private String token;
    private Long   expireSecond;
    private String depName;
    private RoleVO role;
    private Long   depId;
}
