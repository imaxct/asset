package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import me.maxct.asset.constant.AppConst;

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
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime gmtModified;

    @Column(length = 30)
    private String        name;

    @Column(length = 64)
    private String        username;

    @JsonIgnore
    @Column(length = 64)
    private String        password;

    private Long          depId;

    private Long          roleId;
}
