package org.mass.framework.redis.constant;

import org.mass.framework.common.utils.PropertiesConfigUtil;

import java.util.Properties;

/**
 * Created by Allen on 2016/8/10.
 */
public class SystemCacheProperties {

    static Properties properties = PropertiesConfigUtil.getPropertis("systemCache.properties");

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
