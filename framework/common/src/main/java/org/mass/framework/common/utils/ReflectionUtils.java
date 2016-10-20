package org.mass.framework.common.utils;

import org.apache.commons.lang.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 2016-02-03.
 */
public class ReflectionUtils {

    public static String GETTER = "get";

    public static String SETTER = "set";

    public static String getterMethodName(String field) {
        if (!StringUtils.isEmpty(field)) {
            return GETTER + field.substring(0,1).toUpperCase() + field.substring(1,field.length());
        }
        return null;
    }

    public static String setterMethodName(String field) {
        if (!StringUtils.isEmpty(field)) {
            return SETTER + field.substring(0,1).toUpperCase() + field.substring(1,field.length());
        }
        return null;
    }

    public static Map<String, Object> describe(Object obj) {

        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("describe Error " + e);
        }
        return map;
    }

}
