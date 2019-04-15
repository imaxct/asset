package me.maxct.asset.dto;

import java.util.List;

import lombok.Data;
import me.maxct.asset.enumerate.PropertyStatus;
import me.maxct.asset.enumerate.TransferType;

/**
 * @author imaxct
 * 2019-04-03 18:42
 */
@Data
public class ProcessDO {
    private String         name;
    private TransferType   transferType;
    private String         statusRequired;
    private PropertyStatus finalStatus;
    private String         roleRequired;
    private List<StepDO>   step;
}
