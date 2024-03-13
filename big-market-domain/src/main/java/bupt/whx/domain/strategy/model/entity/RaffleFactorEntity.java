package bupt.whx.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @description 抽奖因子实体
 * @create
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
