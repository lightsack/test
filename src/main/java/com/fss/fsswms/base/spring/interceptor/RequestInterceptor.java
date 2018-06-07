package com.fss.fsswms.base.spring.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fss.fsswms.base.annotation.ReqInfo;
import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.base.exception.RndException;
import com.fss.fsswms.base.persistence.CmnConstants;
import com.fss.fsswms.base.util.StringUtil;

public class RequestInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(RequestInterceptor.class);
//	private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile("'(\\s)*(and|or)(\\s)*'", Pattern.CASE_INSENSITIVE);
	
	
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Box paramBox = new Box();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			String[] values = request.getParameterValues(key);
			if (values != null) {
				if(values.length > 1) {
					paramBox.put(key, values);
				} else {
					/*
					String value = URLDecoder.decode(values[0], CmnConstants.CHARSET);
					Matcher matcher = SQL_INJECTION_PATTERN.matcher(value);
					if(matcher.find()) {
						throw new OmbsException(CmnConstants.RES_CD_VALIDATION_ERROR);
					}
					*/
					paramBox.put(key, values[0]);
				}
			}
		}
		request.setAttribute(CmnConstants.REQUEST_PARAM, paramBox);
		Box newParamBox = new Box(paramBox);
		request.setAttribute(CmnConstants.REQUEST_PARAM_ORG, newParamBox);
		paramBox.setSession(request.getSession());
		
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		ReqInfo reqInfo = handlerMethod.getMethodAnnotation(ReqInfo.class);
		if(reqInfo != null) {
			String validForm = reqInfo.validForm();
			if(StringUtil.isNotEmpty(validForm)) {
//				log.debug("Validation Form Name : {}", validForm);
				BindingResult errors = new MapBindingResult(paramBox, validForm);
//				configBeanValidator.setFormName(validForm);
				try {
//					configBeanValidator.validate(paramBox, errors);
				} catch (Exception e) {
					throw new RndException("RES_CD_VALIDATION_ERROR");
				}
				if(errors.hasErrors()) {
					log.info("Validation Error : {}", errors);
					FieldError fieldError = errors.getFieldError();
					String errorCode = fieldError.getCode();
					throw new RndException(errorCode, fieldError.getArguments());
				}
			}
		}
		return true;
	}
	
}

