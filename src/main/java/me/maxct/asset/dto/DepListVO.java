package me.maxct.asset.dto;

import java.util.List;

import lombok.Data;

/**
 * @author imaxct
 * 2019-04-18 10:49
 */
@Data
public class DepListVO {
    private List<DepVO> list;
    private DepVO       tree;
}
