package com.fss.fsswms.mvc.classic.cmn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.mvc.classic.cmn.service.LoginService;

@Controller
@RequestMapping("classic/cmn/login")
public class LoginController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	
	/**
	 * 로그인 화면
	 * 
	 * @param paramBox
	 * @param modelMap
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("login.do")
	public String login(Box paramBox, ModelMap modelMap) throws Exception {
		return "noframe:/classic/cmn/login/login";
	}
	
	/**
	 * 로그인
	 * 
	 * @param paramBox
	 * @param modelMap
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("loginProc.json")
	public void loginProc(Box paramBox, ModelMap modelMap) throws Exception {
//		Thread.sleep(5000);
		loginService.loginProc(paramBox, modelMap);
	}
	
	
	/**
	 * 로그아웃
	 * 
	 * @param paramBox
	 * @param modelMap
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("logout.json")
	public void logout(Box paramBox, ModelMap modelMap) throws Exception {
		loginService.logout();
//		return "redirect:/cmn/login/login.do";
	}
	
}
