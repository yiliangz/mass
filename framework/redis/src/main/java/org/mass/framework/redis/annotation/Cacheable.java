package org.mass.framework.redis.annotation;

import org.mass.framework.redis.constant.DateUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Allen on 2016/1/9.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
    String category() default "";
    String key();
    //默认的缓存时间为永久
    int expire() default -1;
    DateUnit dateUnit() default DateUnit.SECONDS;
}

