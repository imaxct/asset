package me.maxct.asset.domain;

import javax.persistence.*;

import lombok.Data;

/**
 * @author imaxct
 * 2019-03-23 22:41
 */
@Data
@Entity
@Table(name = "asset_user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long roleId;
}
