package org.mass.framework.common.utils;

import java.util.Properties;

/**
 * Created by Allen on 2016-01-26.
 */
public class SystemConfig {

    static Properties properties = PropertiesConfigUtil.getPropertis("sysconfig.properties");

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
