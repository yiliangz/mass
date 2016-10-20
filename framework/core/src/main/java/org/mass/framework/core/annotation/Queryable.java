package org.mass.framework.core.annotation;

import java.lang.annotation.*;

/**
 * Created by Allen on 2015/12/30.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Queryable {

}
