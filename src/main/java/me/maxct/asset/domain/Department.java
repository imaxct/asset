package me.maxct.asset.domain;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 部门
 * @author imaxct
 * 2019-03-12 10:39
 */
@Data
public class Department {
    private Long id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String name;
}
