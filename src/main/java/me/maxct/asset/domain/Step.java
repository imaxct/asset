package me.maxct.asset.domain;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 处理步骤元数据
 * @author imaxct
 * 2019-03-12 09:57
 */
@Data
public class Step {
    private Long          id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String        name;
    private String        authRequired;
    private Long          processId;
    private Long          nextStepId;
}