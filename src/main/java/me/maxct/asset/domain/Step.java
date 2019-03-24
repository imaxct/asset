package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Data;

/**
 * 处理步骤元数据
 * @author imaxct
 * 2019-03-12 09:57
 */
@Data
@Entity
@Table(name = "asset_step")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    @Column(length = 64)
    private String        name;
    private Long          processId;
    private Long          nextStepId;
    @Column(length = 200)
    private String        roleRequired;
}
