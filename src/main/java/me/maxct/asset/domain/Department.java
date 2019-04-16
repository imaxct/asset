package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import me.maxct.asset.constant.AppConst;

/**
 * 部门
 * @author imaxct
 * 2019-03-12 10:39
 */
@Data
@Entity
@Table(name = "asset_department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime gmtModified;

    @Column(length = 64)
    private String        name;
}
