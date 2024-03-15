package bupt.whx.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @description 策略奖品库存Key标识值对象
 * @create
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardStockKeyVO {

    // 策略ID
    private Long strategyId;
    // 奖品ID
    private Integer awardId;

}
