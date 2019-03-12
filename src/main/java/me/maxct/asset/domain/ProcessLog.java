package me.maxct.asset.domain;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 处理记录
 * @author imaxct
 * 2019-03-12 09:59
 */
@Data
public class ProcessLog {
    private Long          id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Long          processUserId;
    private Long          processId;
    private Long          stepId;
    private Long          propertyId;
    private Long          ticketId;
    private boolean       pass;
    private String        processProposal;
}
