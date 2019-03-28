package me.maxct.asset.dto;

import lombok.Data;

/**
 * @author imaxct
 * 2019-03-22 22:56
 */
@Data
public class Msg<T> {
    private boolean ok  = false;
    private String  msg = null;
    private T       obj;

    public static <T> Msg ok(String msg, T obj) {
        Msg m = new Msg();
        m.ok = true;
        m.msg = msg;
        m.obj = obj;
        return m;
    }

    public static <T> Msg<T> ok(T obj) {
        Msg<T> msg = new Msg<>();
        msg.ok = true;
        msg.obj = obj;
        return msg;
    }

    public static Msg err(String msg) {
        Msg m = new Msg();
        m.msg = msg;
        return m;
    }
}
