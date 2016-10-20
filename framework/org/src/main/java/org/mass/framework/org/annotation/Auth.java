package org.mass.framework.org.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented 
@Inherited
public @interface  Auth {

	/**
	 * 是否验证登陆 true=验证 ,false = 不验证
	 * @return
	 */
	 public boolean verifyLogin() default false;
	 
	 
	 /**
	 * 是否验证URL true=验证 ,false = 不验证
	 * @return
	 */
	 public boolean verifyURL() default false;

}
