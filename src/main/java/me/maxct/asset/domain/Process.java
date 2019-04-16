package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import me.maxct.asset.constant.AppConst;
import me.maxct.asset.enumerate.PropertyStatus;
import me.maxct.asset.enumerate.TransferType;

/**
 * 处理流程元数据
 * @author imaxct
 * 2019-03-12 09:56
 */
@Data
@Entity
@Table(name = "asset_process")
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long           id;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime  gmtCreate;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime  gmtModified;

    @Column(length = 64)
    private String         name;

    @Column(length = 200)
    private String         statusRequired;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private PropertyStatus finalStatus;

    private Long           firstStepId;

    @Column(length = 200)
    private String         roleRequired;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private TransferType   transferType;
}
