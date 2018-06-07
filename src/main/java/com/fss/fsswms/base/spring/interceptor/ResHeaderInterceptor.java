package com.fss.fsswms.base.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ResHeaderInterceptor extends HandlerInterceptorAdapter {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ResHeaderInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("X-Frame-Options", "SAMEORIGIN");
		return true;
	}
	
}

