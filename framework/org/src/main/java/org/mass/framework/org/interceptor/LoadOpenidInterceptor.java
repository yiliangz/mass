package org.mass.framework.org.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoadOpenidInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		String openid = session.getAttribute("fromopenid") == null ? null : (String)session.getAttribute("fromopenid");
		String requestOpenid = request.getParameter("req_openid");
		if (!StringUtils.isEmpty(requestOpenid)) {
			session.setAttribute("fromopenid", requestOpenid);
		}
		return super.preHandle(request, response, handler);
	}
	
	
}
