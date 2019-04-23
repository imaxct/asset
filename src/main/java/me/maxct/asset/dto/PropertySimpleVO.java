package me.maxct.asset.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.maxct.asset.constant.AppConst;
import me.maxct.asset.enumerate.PropertyStatus;

/**
 * @author imaxct
 * 2019-04-05 14:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertySimpleVO {
    private Long           id;
    private String         name;
    private PropertyStatus curStatus;
    private String         propertyId;
    private String         curProcess;
    private Long           occupyUserId;
    private String         occupyUserName;
    private Long           depId;
    private String         depName;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime  gmtCreate;
    @JsonFormat(pattern = AppConst.DATE_TIME_FORMAT, timezone = AppConst.TIME_ZONE)
    private LocalDateTime  gmtModified;
}
