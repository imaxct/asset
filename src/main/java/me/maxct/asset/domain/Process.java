package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Data;

/**
 * 处理流程元数据
 * @author imaxct
 * 2019-03-12 09:56
 */
@Data
@Entity
@Table(name = "asset_process")
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String        name;
}
