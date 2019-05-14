package me.maxct.asset.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.maxct.asset.domain.Step;
import me.maxct.asset.domain.Ticket;

/**
 * @author imaxct
 * 2019-03-27 12:22
 */
@Data
@NoArgsConstructor
public class TicketVO {
    private Ticket             ticket;
    private List<ProcessLogVO> logs;
    private String             propertyId;
    private String             propertyName;
    private String             processName;
    private String             applyUserName;
    private Step               curStep;

    public TicketVO(Ticket ticket, String propertyId, String propertyName) {
        this.ticket = ticket;
        this.propertyId = propertyId;
        this.propertyName = propertyName;
    }

    public TicketVO(Ticket ticket, String propertyId, String propertyName, String applyUserName,
                    Step step) {
        this.ticket = ticket;
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.applyUserName = applyUserName;
        this.curStep = step;
    }

    public TicketVO(Ticket ticket, String propertyId, String propertyName, String applyUserName,
                    Step step, String processName) {
        this.ticket = ticket;
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.applyUserName = applyUserName;
        this.curStep = step;
        this.processName = processName;
    }
}
