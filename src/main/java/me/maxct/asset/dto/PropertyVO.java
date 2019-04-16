package me.maxct.asset.dto;

import lombok.Data;
import me.maxct.asset.domain.Step;
import me.maxct.asset.domain.Ticket;

/**
 * 资产详情页数据聚合
 * @author imaxct
 * 2019-03-30 14:19
 */
@Data
public class PropertyVO {

    private PropertySimpleVO property;

    /**
     * 正在进行的工单
     */
    private Ticket           ticket;

    /**
     * 当前用户可操作的步骤
     */
    private Step             step;
}
