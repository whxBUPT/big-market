package bupt.whx.domain.strategy.service.annotation;

import bupt.whx.domain.strategy.service.rule.factory.DefaultLogicFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:LogicStrategy
 * Package:bupt.whx.domain.strategy.service.annotation
 * Description:
 *
 * @Author whx
 * @Create 2024/3/5 15:10
 * @Version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogicStrategy {

    DefaultLogicFactory.LogicModel logicMode();

}

