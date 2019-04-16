package me.maxct.asset.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.maxct.asset.constant.AppConst;

/**
 * @author imaxct
 * 2019-04-15 21:37
 */
@Data
@AllArgsConstructor
public class ProcessLogVO {
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime gmtCreate;
    private String        stepName;
    private String        processUser;
    private boolean       pass;
    private String        processProposal;
}
