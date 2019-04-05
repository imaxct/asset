package me.maxct.asset.dto;

import lombok.Data;
import me.maxct.asset.enumerate.PropertyStatus;

/**
 * @author imaxct
 * 2019-03-28 23:19
 */
@Data
public class TicketDO {
    private Long           propertyId;
    private Long           processId;
    private Long           transferUserId;
    private String         applyReason;
    private PropertyStatus finalStatus;
}
