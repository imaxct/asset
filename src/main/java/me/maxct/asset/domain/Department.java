package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Data;

/**
 * 部门
 * @author imaxct
 * 2019-03-12 10:39
 */
@Data
@Entity
@Table(name = "asset_department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    @Column(length = 64)
    private String        name;
}
