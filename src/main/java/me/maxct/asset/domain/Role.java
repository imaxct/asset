package me.maxct.asset.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Data;

/**
 * @author imaxct
 * 2019-03-21 11:18
 */
@Data
@Entity
@Table(name = "asset_table")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String        name;
    private String        authorizedMapping;
}
