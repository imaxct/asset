package me.maxct.asset.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

/**
 * @author imaxct
 * 2019-03-21 11:19
 */
@Data
@Entity
@Table(name = "asset_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String        name;
    private String        username;
    private String        password;
    private Long          depId;
    private List<Role>    roles;
}
