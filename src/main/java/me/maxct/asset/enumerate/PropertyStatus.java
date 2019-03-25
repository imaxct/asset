package me.maxct.asset.enumerate;

import lombok.Getter;

/**
 * 资产状态
 * @author imaxct
 * 2019-03-12 10:51
 */
@Getter
public enum PropertyStatus {
    PENDING_IMPORT("PENDING_IMPORT","待入库"),
    IDLE("IDLE", "空闲"),
    OCCUPIED("OCCUPIED", "使用中"),
    PROCESSING("PROCESSING", "流程处理中");

    private String name;
    private String desc;

    PropertyStatus(String name, String desc){
        this.name = name;
        this.desc = desc;
    }
}
