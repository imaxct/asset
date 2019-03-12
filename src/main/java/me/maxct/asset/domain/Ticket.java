package me.maxct.asset.domain;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 工单
 * @author imaxct
 * 2019-03-12 10:01
 */
@Data
public class Ticket {
    private Long          id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Long          applyUserId;
    private Long          curStepId;
    private String        curStatus;
    private String        applyReason;
}
