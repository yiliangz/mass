package org.mass.framework.common.bean;

import java.util.HashMap;

/**
 * Created by Allen on 2016-01-05.
 */
public class ResponseMap extends HashMap<String,Object> {

    public ResponseMap() {
        this.msg("0","success");
    }

    public static ResponseMap getSuccess() {
        return get("0","success");
    }

    public static ResponseMap getSuccess(String msg) {
        return get("0",msg);
    }

    public static ResponseMap getError() {
        return get("1","error");
    }

    public static ResponseMap getError(String msg) {
        return get("1", msg);
    }

    private static ResponseMap get(String errorCode,String errorMsg) {
        ResponseMap responseMap = new ResponseMap();
        responseMap.put("errorCode", errorCode);
        responseMap.put("errorMsg", errorMsg);
        return responseMap;
    }

    public ResponseMap success(Object errorMsg) {
        this.msg("0",errorMsg);
        return this;
    }

    public ResponseMap success() {
        this.msg("0","success");
        return this;
    }

    public ResponseMap error(Object errorMsg) {
        this.msg("1",errorMsg);
        return this;
    }

    public ResponseMap error() {
        this.msg("1","error");
        return this;
    }

    public ResponseMap msg(String errorCode,Object errorMsg) {
        this.put("errorCode", errorCode);
        this.put("errorMsg", errorMsg);
        return this;
    }

}
