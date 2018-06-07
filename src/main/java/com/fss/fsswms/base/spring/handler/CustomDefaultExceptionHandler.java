package com.fss.fsswms.base.spring.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.fss.fsswms.base.spring.view.HtmlView;
import com.fss.fsswms.base.spring.view.JsonView;
import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.base.exception.NoAuthException;
import com.fss.fsswms.base.exception.ResourceNotFoundException;
import com.fss.fsswms.base.exception.RndException;
import com.fss.fsswms.base.persistence.CmnConstants;
import com.fss.fsswms.base.util.ExcelUtil;
import com.fss.fsswms.base.util.JsonUtil;
import com.fss.fsswms.base.util.MsgUtil;
import com.fss.fsswms.base.util.SessionUtil;
import com.fss.fsswms.base.util.StringUtil;
import com.fss.fsswms.base.util.WebUtil;
import com.fss.fsswms.mvc.classic.cmn.vo.user.UserVO;

@ControllerAdvice
public class CustomDefaultExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(CustomDefaultExceptionHandler.class);

	@Autowired
	private JsonView jsonView;
		
	@Autowired
	private HtmlView htmlView;
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
		preHandleException(e, request, response);
		
		String msgId = null, msg = null;
		Object[] messageParams = null;
		if(e instanceof RndException) {
			RndException oe = (RndException)e;
			msgId = oe.getMessageId();
			messageParams = oe.getMessageParams();
		} else {
//			msgId = CmnConstants.RES_CD_SERVER_ERROR;
		}
		msg = MsgUtil.getMsg(msgId, messageParams);
		if(StringUtil.isEmpty(msg)) {
			msg = e.getMessage();
		}
		log.error("Exception ID : {}", msgId);
		log.error("Exception Message : {}", msg);
		if(!(e instanceof RndException)) {
			log.error("Exception Detail : {}", e);
		}
		
		Box box = new Box();
//		box.put(CmnConstants.RES_CODE, msgId);
		box.put(CmnConstants.RES_MSG, msg);
		
		if(ExcelUtil.isExcel(request) && "MSIE".equals(WebUtil.getBrowser(request))) {
			return new ModelAndView(htmlView, box);
		} else if(JsonUtil.isJson(request) || ExcelUtil.isExcel(request)) {
			return new ModelAndView(jsonView, box);
		}
		return new ModelAndView("cmn/error/RndException", box);
	}
	
	@ExceptionHandler(NoAuthException.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleNoAuthException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
		preHandleException(e, request, response);
		
//		NoAuthException nae = (NoAuthException)e;
		if(JsonUtil.isJson(request)) {
			Box box = new Box();
			box.put(CmnConstants.RES_CODE, "SESSION_OUT");
			box.put(CmnConstants.RES_MSG, "세션이 만료 되었습니다. 다시 로그인 하십시오.");
			return new ModelAndView(jsonView, box);
		}
		return new ModelAndView("redirect:/cmn/login/login.do");
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleNoResourceNotFoundException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
		preHandleException(e, request, response);
		
//		String msgId = CmnConstants.RES_CD_METHOD_NOT_ALLOWED;
		String msg = "리소스를 찾을 수 없음";
		
//		log.error("Exception ID : {}", msgId);
		log.error("Exception Message : {}", msg);
		
		Box box = new Box();
//		box.put(CmnConstants.RES_CODE, msgId);
		box.put(CmnConstants.RES_MSG, msg);
		if(JsonUtil.isJson(request)) {
			return new ModelAndView(jsonView, box);
		}
		
		UserVO userVO = SessionUtil.getUser(request);
		if(userVO == null) {
			return new ModelAndView("noframe:/cmn/error/resourceNotFound", box);
		} else {
			return new ModelAndView("cmn/error/resourceNotFound", box);
		}
	}
	
	private void preHandleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}
	}
	
}
