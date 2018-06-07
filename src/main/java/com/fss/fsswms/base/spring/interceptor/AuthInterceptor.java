package com.fss.fsswms.base.spring.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fss.fsswms.base.exception.NoAuthException;
import com.fss.fsswms.base.util.JsonUtil;
import com.fss.fsswms.base.util.SessionUtil;
import com.fss.fsswms.mvc.classic.cmn.vo.user.UserVO;
import com.fss.fsswms.mvc.modern.cmn.vo.user.GodCustVO;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		UserVO userVO = (UserVO)SessionUtil.getUser(request);
		GodCustVO godCustVO = (GodCustVO)SessionUtil.getGodCust(request);
		HttpSession session = request.getSession();
		if (userVO == null && godCustVO == null) {
			SessionUtil.invalidate(session);
//			log.debug("AuthInterceptor >>> {}", request);
			if (JsonUtil.isJson(request)) {
				throw new NoAuthException();
			} else {
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('세션이 만료되어 로그인 페이지로 이동합니다.');");
				out.println(" if( opener ) {");
				out.println("   opener.top.location.replace('" +request.getContextPath() + "');");
				out.println("   self.close(); ");
				out.println(" } else { ");
				out.println("   top.location.replace('" +request.getContextPath() + "/index.jsp');");
				out.println(" } ");
				out.println("</script>");
				out.flush();
				return false;
			}
		}
		return true;
	}
	
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//		HttpSession session = request.getSession();
//		Box paramBox = (Box)request.getAttribute(CmnConstants.REQUEST_PARAM);
//		
//		log.debug("paramBox >>> {}", paramBox);
//	}
	
}

