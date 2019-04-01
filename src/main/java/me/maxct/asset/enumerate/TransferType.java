package me.maxct.asset.enumerate;

import lombok.Getter;

/**
 * @author imaxct
 * 2019-04-01 14:51
 */
@Getter
public enum TransferType {
    APPLY_USER("APPLY_USER", "流程发起人"),
    STEP_HANDLER("STEP_HANDLER", "步骤处理人"),
    SPECIFIC_USER("SPECIFIC_USER","指定用户");
    private String code;
    private String desc;
    TransferType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
