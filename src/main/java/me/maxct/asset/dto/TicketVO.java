package me.maxct.asset.dto;

import java.util.List;

import lombok.Data;
import me.maxct.asset.domain.ProcessLog;
import me.maxct.asset.domain.Ticket;

/**
 * @author imaxct
 * 2019-03-27 12:22
 */
@Data
public class TicketVO {
    private Ticket           ticket;
    private List<ProcessLog> logs;
}
