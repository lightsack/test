package com.fss.fsswms.base.spring.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.base.persistence.CmnConstants;

public class BoxHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public boolean supportsParameter(MethodParameter mthodParameter) {
    	Class<?> paramType = mthodParameter.getParameterType();
    	return (Box.class.isAssignableFrom(paramType));
    }
    
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		Box paramBox = (Box)request.getAttribute(CmnConstants.REQUEST_PARAM);
		return paramBox;
    }

    /*
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		Box paramBox = new Box();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			String[] values = request.getParameterValues(key);
			if (values != null) {
				paramBox.put(key, (values.length > 1) ? values : values[0]);
			}
		}
		request.setAttribute(CmnConstants.REQUEST_PARAM, paramBox);
		Box newParamBox = new Box(paramBox);
		request.setAttribute(CmnConstants.REQUEST_PARAM_ORG, newParamBox);
		paramBox.setSession(request.getSession());
		return paramBox;
    }
    */
    
}
