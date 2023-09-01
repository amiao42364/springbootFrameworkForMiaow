package cn.miaow.framework.aspectj.annotation;

import cn.miaow.framework.constant.CacheConstants;
import cn.miaow.framework.constant.enums.LimitType;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @author miaow
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * 限流key
     */
    String key() default CacheConstants.RATE_LIMIT_KEY;

    /**
     * 限流时间,单位秒
     */
    int time() default 60;

    /**
     * 限流次数
     */
    int count() default 100;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;
}
