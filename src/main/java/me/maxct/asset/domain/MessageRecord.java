package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author imaxct
 * 2019-05-09 21:10
 */
@Data
@Entity
@Table(name = "asset_user_message")
public class MessageRecord {
    private Long          id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Long          userId;
    private Long          msgId;
}
