package bupt.whx.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:RaffleFactorEntity
 * Package:bupt.whx.domain.strategy.model.entity
 * Description:     抽奖因子实体
 *
 * @Author whx
 * @Create 2024/3/5 14:21
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleFactorEntity {

    /** 用户ID */
    private String userId;
    /** 策略ID */
    private Long strategyId;

}

