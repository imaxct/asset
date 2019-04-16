package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import me.maxct.asset.constant.AppConst;
import me.maxct.asset.enumerate.PropertyStatus;

/**
 * 处理步骤元数据
 * @author imaxct
 * 2019-03-12 09:57
 */
@Data
@Entity
@Table(name = "asset_step")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long           id;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime  gmtCreate;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime  gmtModified;

    @Column(length = 64)
    private String         name;
    private Long           processId;
    private Long           nextStepId;
    @Column(length = 200)
    private String         roleRequired;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private PropertyStatus statusRequired;
}
