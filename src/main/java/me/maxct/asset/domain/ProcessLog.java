package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import me.maxct.asset.constant.AppConst;

/**
 * 处理记录
 * @author imaxct
 * 2019-03-12 09:59
 */
@Data
@Entity
@Table(name = "asset_process_log")
public class ProcessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime gmtModified;
    private Long          processUserId;
    private Long          processId;
    private Long          stepId;
    private Long          propertyId;
    private Long          ticketId;
    private boolean       pass;

    @Column(length = 200)
    private String        processProposal;
}
