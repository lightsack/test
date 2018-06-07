package com.fss.fsswms.base.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.base.persistence.CmnConstants;
import com.fss.fsswms.mvc.classic.cmn.vo.user.UserVO;
import com.fss.fsswms.mvc.modern.cmn.vo.user.GodCustVO;

public class SessionUtil {

	private SessionUtil() {
	}
	
	public static UserVO getUser(HttpSession session) {
		return (UserVO)session.getAttribute(CmnConstants.SESSION_USER);
	}
	
	public static String getUserId(HttpSession session) {
		return session.getAttribute(CmnConstants.SESSION_USER_ID).toString();
	}
	
	public static UserVO getUser(HttpServletRequest request) {
		return getUser(request.getSession());
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Box> getCodeList(HttpSession session) {
		return (List<Box>)session.getAttribute(CmnConstants.SESSION_USER_CODE_LIST);
	}
	
	public static void setUser(HttpSession session, UserVO userVO) {
		session.setAttribute(CmnConstants.SESSION_USER, userVO);
		session.setAttribute(CmnConstants.SESSION_USER_ID, userVO.getUserId());
		session.setAttribute(CmnConstants.SESSION_USER_NM, userVO.getUserNm());
	}
	
	public static void setUser(HttpServletRequest request, UserVO userVO) {
		setUser(request.getSession(), userVO);
	}
	
	public static void setUserProgAuthList(HttpServletRequest request, List<Box> userProgAuthList) {
		setUserProgAuthList(request.getSession(), userProgAuthList);
	}
	
	public static void setUserProgAuthList(HttpSession session, List<Box> userProgAuthList) {
		session.setAttribute(CmnConstants.SESSION_USER_PROG_AUTH_LIST, userProgAuthList);
	}
	
	public static void setCodeList(HttpServletRequest request, List<Box> codeList) {
		setCodeList(request.getSession(), codeList);
	}
	
	public static void setCodeList(HttpSession session, List<Box> codeList) {
		session.setAttribute(CmnConstants.SESSION_USER_CODE_LIST, codeList);
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Box> getWhClsList(HttpSession session) {
		return (List<Box>)session.getAttribute(CmnConstants.SESSION_WH_CLS_LIST);
	}
	public static void setWhClsList(HttpSession session, List<Box> codeList) {
		session.setAttribute(CmnConstants.SESSION_WH_CLS_LIST, codeList);
	}
	
	
	public static String getPsptNo(HttpSession session) {
		return session.getAttribute(CmnConstants.SESSION_PSPT_NO).toString();
	}
	
	
	
	public static GodCustVO getGodCust(HttpSession session) {
		return (GodCustVO)session.getAttribute(CmnConstants.SESSION_CUST);
	}
	
	public static GodCustVO getGodCust(HttpServletRequest request) {
		return getGodCust(request.getSession());
	}
	
	
	
	
	
	
	public static void invalidate(HttpSession session) {
		clear(session);
		session.invalidate();
	}
	
	private static void clear(HttpSession session) {
		session.removeAttribute(CmnConstants.SESSION_USER);
		session.removeAttribute(CmnConstants.SESSION_USER_ID);
		session.removeAttribute(CmnConstants.SESSION_USER_NM);
		session.removeAttribute(CmnConstants.SESSION_CUST);
//		session.removeAttribute(CmnConstants.SESSION_USER_PROG_AUTH_LIST);
//		session.removeAttribute(CmnConstants.SESSION_USER_CODE_LIST);
//		session.removeAttribute(CmnConstants.SESSION_WH_CLS_LIST);
	}
	
}
