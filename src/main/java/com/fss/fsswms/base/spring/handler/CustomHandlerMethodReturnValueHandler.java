package com.fss.fsswms.base.spring.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.View;

import com.fss.fsswms.base.util.ExcelUtil;
import com.fss.fsswms.base.util.HttpUtil;
import com.fss.fsswms.base.util.JsonUtil;

public class CustomHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

	private static final Logger log = LoggerFactory.getLogger(CustomHandlerMethodReturnValueHandler.class);
	
	private View jsonView;
	private View excelView;
	
	public void setJsonView(View jsonView) {
		this.jsonView = jsonView;
	}
	
	public void setExcelView(View excelView) {
		this.excelView = excelView;
	}

	public boolean supportsReturnType(MethodParameter returnType) {
		Class<?> paramType = returnType.getParameterType();
		return void.class.equals(paramType);
	}

	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
		if(returnValue == null) {
			HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
			if(ExcelUtil.isExcel(request)) {
				mavContainer.setView(excelView);
				return;
			}
			if(JsonUtil.isJson(request)) {
				String uri = request.getRequestURI();
				if(uri.endsWith(".json")) {
					mavContainer.setView(jsonView);
				}
				return;
			} 
		}
	}
	
}
