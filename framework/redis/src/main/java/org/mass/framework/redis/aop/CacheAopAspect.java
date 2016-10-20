package org.mass.framework.redis.aop;

import org.mass.framework.redis.annotation.Cacheable;
import org.mass.framework.redis.constant.DateUnit;
import org.mass.framework.redis.constant.SystemCacheProperties;
import org.mass.framework.redis.util.AopUtils;
import org.mass.framework.redis.util.RedisUtils;
import org.mass.framework.redis.util.SpringExpressionUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by Allen on 2016/1/9.
 */
public class CacheAopAspect {

    private final static Logger log =  Logger.getLogger(CacheAopAspect.class);

    public Object doCacheable(ProceedingJoinPoint pjp) throws Throwable {
        Object result=null;
        Method method = AopUtils.getMethod(pjp);

        Cacheable cacheable = method.getAnnotation(Cacheable.class);

        Boolean isCacheEnable = "enable".equals(SystemCacheProperties.getProperty("system.cache.enable"));

        if(cacheable != null && !isCacheEnable) {
            log.debug("没有开启缓存");
        }

        if (cacheable == null || !isCacheEnable) {
            try {
                result = pjp.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return result;
        }

        String key = cacheable.key();
        String keyVal = SpringExpressionUtils.parseKey(key, method, pjp.getArgs());

        if (!StringUtils.isEmpty(cacheable.category())){
            keyVal = cacheable.category() + "_" + keyVal;
        } else {
            keyVal = pjp.getTarget().getClass().getSimpleName() + "_"
                    + method.getName() + "_" + keyVal;
        }

        Class returnType = ((MethodSignature)pjp.getSignature()).getReturnType();

        result = RedisUtils.get(keyVal, returnType);

        if (result == null) {
            try {
                result = pjp.proceed();
                int expireSeconds = 0;
                if (cacheable.expire() == -1 &&
                        "enable".equals(SystemCacheProperties.getProperty("system.cache.expire.default.enable"))) {
                    expireSeconds = new Integer(SystemCacheProperties.getProperty("system.cache.expire.default.seconds"));
                } else {
                    expireSeconds = getExpireSeconds(cacheable);
                }
                RedisUtils.set(keyVal, result, expireSeconds);
                log.debug("已缓存缓存:key=" +  keyVal);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return result;
        }
        log.debug("========从缓存中读取");
        log.debug("=======:key   = " + key);
        log.debug("=======:keyVal= " + keyVal);
        log.debug("=======:val   = " + result);
        return result;
    }

    public int getExpireSeconds(Cacheable cacheable) {
        int expire = cacheable.expire();
        DateUnit unit = cacheable.dateUnit();
        if (expire <= 0) {
            return 0;
        }
        if (unit == DateUnit.MINUTES) {
            return expire * 60;
        } else if(unit == DateUnit.HOURS) {
            return expire * 60 * 60;
        } else if(unit == DateUnit.DAYS) {
            return expire * 60 * 60 * 24;
        } else if(unit == DateUnit.MONTHS) {
            return expire * 60 * 60 * 24 * 30;
        } else if(unit == DateUnit.YEARS) {
            return expire * 60 * 60 * 24 * 365;
        }
        return expire;
    }


}
