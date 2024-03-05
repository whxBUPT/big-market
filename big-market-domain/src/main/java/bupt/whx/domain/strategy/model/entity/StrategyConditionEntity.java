package bupt.whx.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:StrategyConfitionEntity
 * Package:bupt.whx.domain.strategy.model.entity
 * Description:
 *
 * @Author whx
 * @Create 2024/3/5 14:24
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyConditionEntity {

    /** 用户ID */
    private String userId;
    /** 策略ID */
    private Integer strategyId;

}

