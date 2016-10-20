package org.mass.framework.common.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfigUtil {

	static Logger logger = Logger.getLogger(PropertiesConfigUtil.class.getName());
	
    public static Properties getPropertis(String fileName) {
        try {
            Properties properties = new Properties();

            InputStream in = PropertiesConfigUtil.class.getClassLoader().
                             getResourceAsStream(fileName);
            properties.load(in);
            in.close();
            return properties;
        } catch (IOException e) {
        	logger.error("load " + fileName +  " error!");
            e.printStackTrace();
        }
		return null;   	
    }

    
}
