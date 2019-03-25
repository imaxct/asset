package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Data;
import me.maxct.asset.enumerate.PropertyStatus;

/**
 * 资产
 * @author imaxct
 * 2019-03-12 10:41
 */
@Data
@Entity
@Table(name = "asset_property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long           id;
    private LocalDateTime  gmtCreate;
    private LocalDateTime  gmtModified;

    @Column(length = 64)
    private String         name;

    @Column(length = 64)
    @Enumerated(EnumType.STRING)
    private PropertyStatus curStatus;

    @Column(length = 64)
    private String         propertyId;

    private Long           depId;

    private Long           processId;

    private Long           occupyUserId;
}
