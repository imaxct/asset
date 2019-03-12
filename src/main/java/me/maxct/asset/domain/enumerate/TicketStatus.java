package me.maxct.asset.domain.enumerate;

import lombok.Getter;

/**
 * 工单状态
 * @author imaxct
 * 2019-03-12 10:13
 */
@Getter
public enum TicketStatus {
    SUBMIT(1, "已提交"),
    PROCESSING(2, "审批中"),
    PASS(3, "审批通过"),
    DENY(4, "审批未通过"),
    DONE(5, "工单关闭");
    private int    code;
    private String desc;

    TicketStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
