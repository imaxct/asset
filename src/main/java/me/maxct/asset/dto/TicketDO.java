package me.maxct.asset.dto;

import lombok.Data;

/**
 * @author imaxct
 * 2019-03-28 23:19
 */
@Data
public class TicketDO {
    private Long   propertyId;
    private Long   processId;
    private Long   transferUserId;
    private String applyReason;
}
