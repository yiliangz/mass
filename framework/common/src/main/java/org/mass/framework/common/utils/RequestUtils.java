package org.mass.framework.common.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class RequestUtils {

	public static void setRootMap(HttpServletRequest request,String output,String root) {
		TypeReference<Map<String, Object>> type = new TypeReference<Map<String, Object>>(){};
		Map<String, Object> map = JSONObject.parseObject(output,type);
	    for (String key : map.keySet()) {
	    	if (root == null) {
	    		request.setAttribute(key, map.get(key));
	    	} else if (root!=null && root.equals(key)) {
	    		setRootMap(request,JSONObject.toJSONString(map.get(key)),null);
	    	}
	    }
	}

	public static void setRootMap(HttpServletRequest request,String output) {
		setRootMap(request,output,null);
	}
	
	public static void setRootMap(HttpServletRequest request,Map<String, Object> map) {
	    for (String key : map.keySet()) {
	    	request.setAttribute(key, map.get(key));
	    }		
	}
	
	public static String getContextPath (HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("");
	}
	
}
