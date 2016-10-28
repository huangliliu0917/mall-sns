package com.huotu.huobanplus.sns.annotation;

import java.lang.annotation.*;

/**
 * 标注在MVC的Controller方法参数中，该标注认为该值为当前商户ID
 *
 * @author slt
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomerId {
}
