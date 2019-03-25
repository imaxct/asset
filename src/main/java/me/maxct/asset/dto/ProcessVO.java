package me.maxct.asset.dto;

import java.util.List;

import lombok.Data;
import me.maxct.asset.domain.Process;
import me.maxct.asset.domain.Step;

/**
 * @author imaxct
 * 2019-03-25 21:57
 */
@Data
public class ProcessVO {
    private Process    process;
    private List<Step> steps;
}
