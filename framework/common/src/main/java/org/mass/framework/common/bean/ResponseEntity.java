package org.mass.framework.common.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 2015/7/7.
 */
public class ResponseEntity {

    private Map<String,Object> entity = new HashMap<String,Object>();

    public void put(String key,Object value) {
        entity.put(key, value);
    }

    public Map<String,Object> getEntity() {
        return entity;
    }

    public static Map<String,Object> getSuccess() {
        return get("0","success");
    }

    public static Map<String,Object> getSuccess(String msg) {
        return get("0",msg);
    }

    public static Map<String,Object> getError() {
        return get("1","error");
    }

    public static Map<String, Object> getError(String msg) {
        return get("1", msg);
    }

    public static Map<String,Object> get(String errorCode,String errorMsg) {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("errorCode", errorCode);
        result.put("errorMsg", errorMsg);
        return result;
    }

}

