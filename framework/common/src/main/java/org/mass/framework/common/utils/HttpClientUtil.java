package org.mass.framework.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	public static String DEFAULT_CHARSET = "UTF-8";
	
    public static void main(String[] args) throws Exception {
    	
    	//String url = "https://app.binf.cn/otoorder/oiOrderForOtoAction/getOrderList.do?userCode=15817175804";
    	
    	String url = "http://192.168.5.122:8080/o2ouser/oiUserForOtoAction/queryAddress.do";
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("userCode", "15817175804");
    	String result = post(url,map);
    	System.out.println(result);
    }
    
    
    public static String post(String url,Map map) throws ClientProtocolException, IOException {
    	String result = null;
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	HttpPost httpPost = new HttpPost(url);
    	httpPost.setEntity(new UrlEncodedFormEntity(toPairEntity(map),DEFAULT_CHARSET));
    	CloseableHttpResponse response = httpclient.execute(httpPost);
    	try {
    	    HttpEntity entity = response.getEntity();
    	    result = EntityUtils.toString(entity,DEFAULT_CHARSET);
    	    EntityUtils.consume(entity);
    	} finally {
			httpPost.abort();
    	    response.close();
			httpclient.close();
    	}
    	return result;
    }
    
    public static String post(String url,HttpServletRequest request) throws ClientProtocolException, IOException {
    	return post(url,request.getParameterMap());
    }
    
    public static String get(String url) throws ClientProtocolException, IOException {
    	String result = null;
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	HttpGet httpGet = new HttpGet(url);
    	CloseableHttpResponse response = httpclient.execute(httpGet);
    	try {
    	    HttpEntity entity = response.getEntity();
    	    result = EntityUtils.toString(entity,DEFAULT_CHARSET);
    	    EntityUtils.consume(entity);
    	} finally {
    	    response.close();
    	}	
    	return result;
    }
    
    public static List<NameValuePair> toPairEntity(Map parameterMap) {
    	Map<String,String> map = toNormalMap(parameterMap);
    	List<NameValuePair> nvps = new ArrayList <NameValuePair>();
        for (String key : map.keySet()) {
            nvps.add(new BasicNameValuePair(key,map.get(key)));
        }
    	return nvps;
    }
    
    //request.getParameterMap中的value是数组
    //把request的map转换成普通可用(非数组)的map
    public static Map<String, String> toNormalMap(Map parameterMap) {
		Map<String,String> result = new HashMap<String,String>();
		Iterator entries = parameterMap.entrySet().iterator();
		Map.Entry entry;
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			String name = (String) entry.getKey();
			String value = "";
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			result.put(name, value);
		}
		return result;
    }
    
}
