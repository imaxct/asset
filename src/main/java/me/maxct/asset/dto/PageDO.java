package me.maxct.asset.dto;

import lombok.Data;

/**
 * @author imaxct
 * 2019-04-03 14:11
 */
@Data
public class PageDO<T> {
    private int pageNo;
    private int size;
    private T   data;
}
