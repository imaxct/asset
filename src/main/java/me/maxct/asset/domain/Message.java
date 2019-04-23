package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author imaxct
 * 2019-04-23 16:36
 */
@Data
@Entity
@Table(name = "asset_message")
public class Message {
    private Long          id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String        content;
}
