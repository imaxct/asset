package me.maxct.asset.domain;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 资产
 * @author imaxct
 * 2019-03-12 10:41
 */
@Data
public class Property {
    private Long id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String name;
    private String curStatus;
    private String propertyId;
    private Long depId;
}
