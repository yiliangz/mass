package org.mass.framework.org.interceptor;

import org.mass.framework.common.bean.ResponseEntity;
import org.mass.framework.common.utils.HtmlUtil;
import org.mass.framework.org.annotation.AppAuth;
import org.mass.framework.org.bean.Token;
import org.mass.framework.org.service.TokenService;
import org.mass.framework.org.token.TokenFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class AppAuthInterceptor extends HandlerInterceptorAdapter {

	private final static Logger log = Logger.getLogger(AppAuthInterceptor.class);

	@Resource(name="jdbcTokenFactory")
	private TokenFactory tokenFactory;

	@Resource
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HandlerMethod method = (HandlerMethod)handler;
		AppAuth auth = method.getMethod().getAnnotation(AppAuth.class);
		AppAuth classAuth = method.getBeanType().getAnnotation(AppAuth.class);

		//---------------------------------
		//验证微信信息是否过期
		//---------------------------------
		if( (auth!= null && auth.verifyWx()) || (classAuth!= null && classAuth.verifyWx())){
			if(request.getSession().getAttribute("fromopenid") == null){
				Map<String, Object> result = ResponseEntity.getError("页面已过期，请重新进入");
				HtmlUtil.writerJson(response, result);
				return false;
			}
		}

		//---------------------------------
		//验证token是否过期
		//---------------------------------
		if((auth != null && auth.verifyToken()) || (classAuth != null && classAuth.verifyToken())){
			Token token = getToken(request);
			boolean isValid = tokenFactory.validToken(token);
			if (!isValid) {
				Map<String, Object> result = ResponseEntity.getError("token无效或过期");
				HtmlUtil.writerJson(response, result);
				return false;
			}

		}
		return super.preHandle(request, response, handler);
	}

	public Token getToken(HttpServletRequest request) {
		String tokenCode = request.getParameter("token");
		if (!StringUtils.isEmpty(tokenCode)) {
			return tokenService.getTokenByCode(tokenCode);
		}
		return null;
	}
	
}
