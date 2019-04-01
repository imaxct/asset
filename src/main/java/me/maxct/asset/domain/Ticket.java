package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Data;
import me.maxct.asset.enumerate.TicketStatus;

/**
 * 工单
 * @author imaxct
 * 2019-03-12 10:01
 */
@Data
@Entity
@Table(name = "asset_ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Long          applyUserId;
    private Long          curStepId;
    private Long          processId;
    private Long          propertyId;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private TicketStatus  curStatus;

    @Column(length = 250)
    private String        applyReason;

    private Long          depId;

    private Long          transferUserId;
}
