package org.mass.framework.redis.aop;

import org.mass.framework.redis.annotation.CacheEvict;
import org.mass.framework.redis.util.AopUtils;
import org.mass.framework.redis.util.RedisUtils;
import org.mass.framework.redis.util.SpringExpressionUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * Created by Allen on 2016/8/9.
 */
public class CacheAopEvict {

    private final static Logger log =  Logger.getLogger(CacheAopEvict.class);

    public Object doCacheEvict(ProceedingJoinPoint pjp) throws Throwable {
        Object result=null;
        Method method = AopUtils.getMethod(pjp);

        CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);

        if (cacheEvict != null) {
            String key = cacheEvict.key();
            String keyVal = SpringExpressionUtils.parseKey(key, method, pjp.getArgs());
            keyVal = cacheEvict.category() + "_" + keyVal;
            RedisUtils.del(keyVal);
            log.debug("已删除缓存:key=" + keyVal);
        }
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

}
