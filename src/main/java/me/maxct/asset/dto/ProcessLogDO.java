package me.maxct.asset.dto;

import lombok.Data;

/**
 * @author imaxct
 * 2019-03-29 10:56
 */
@Data
public class ProcessLogDO {
    private Long    stepId;
    private Long    ticketId;
    private boolean pass;
    private String  processProposal;
}
