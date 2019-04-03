package me.maxct.asset.dto;

import lombok.Data;
import me.maxct.asset.enumerate.PropertyStatus;

/**
 * @author imaxct
 * 2019-04-03 18:46
 */
@Data
public class StepDO {
    private String         name;
    private String         roleRequired;
    private PropertyStatus statusRequired;
}
