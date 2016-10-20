package org.mass.framework.org.annotation;

import java.lang.annotation.*;

/**
 * Created by Allen on 2015-12-25 0025.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
@Inherited
public @interface AppAuth {

    /**
     * 是否验证微信 true=验证 ,false = 不验证
     * @return
     */
    public boolean verifyWx() default false;

    /**
     * 是否验证App的token信息 true=验证 ,false = 不验证
     * @return
     */
    public boolean verifyToken() default false;

    /**
     * 是否验证浏览器方式的登陆信息 true=验证 ,false = 不验证
     * @return
     */
    public boolean verifyLogin() default false;

}

