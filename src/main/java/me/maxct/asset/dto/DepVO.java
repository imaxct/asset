package me.maxct.asset.dto;

import java.util.List;

import lombok.Data;
import me.maxct.asset.domain.Department;

/**
 * @author imaxct
 * 2019-04-18 10:20
 */
@Data
public class DepVO {
    private Long        id;
    private String      label;
    private List<DepVO> children;

    public static DepVO parse(Department department) {
        DepVO vo = new DepVO();
        vo.setId(department.getId());
        vo.setLabel(department.getName());
        return vo;
    }
}
