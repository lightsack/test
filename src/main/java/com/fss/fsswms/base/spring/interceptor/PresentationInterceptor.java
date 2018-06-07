package com.fss.fsswms.base.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fss.fsswms.base.util.HttpUtil;
import com.fss.fsswms.base.util.StringUtil;

public class PresentationInterceptor extends HandlerInterceptorAdapter {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(PresentationInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		String viewName = modelAndView.getViewName();
		if(StringUtil.isEmpty(viewName)) {
			return;
		}
		if(viewName.indexOf(":") > -1) {
			return;
		}
		String uri = HttpUtil.getRequestUri(request);
		if(StringUtil.isEmpty(uri)) {
			return;
		}
//		request.setAttribute(CmnConstants.REQUEST_MENU_ID, menuBox.getString("menuId"));
//		request.setAttribute(CmnConstants.REQUEST_TOP_MENU_ID, menuBox.getString("upperMenuId"));
	}
	
}