package me.maxct.asset.dto;

import lombok.Data;

/**
 * @author imaxct
 * 2019-04-03 14:18
 */
@Data
public class UserDO {
    private Long   id;
    private String username;
    private String name;
    private String password;
    private Long   depId;
    private Long   roleId;
}
