package com.fss.fsswms.mvc.classic.cmn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.mvc.classic.cmn.service.MainService;


/**
 * The Class MainController.
 *
 * @Class Name : MainController.java
 * @Description : Main Controller Class
 * @Modification Information
 * 
 * @author 
 * @version 1.0
 * @see  Copyright (C) by LKP All right reserved.
 * @since 2017.08.21
 * @ 
 * @  수정일              수정자            수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2017.08.21   user01      최초생성
 */
@Controller
@RequestMapping("classic/cmn/main")
public class MainController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private MainService mainService;
	
	
	/**
	 * 메인 화면
	 * @param box
	 * @param model
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main.do")
	public String main(Box box, ModelMap model) throws Exception {
		mainService.selectMain(box, model);
		return "main:/classic/cmn/main/main";
	}
	
	/**
	 * 달력 조회
	 * @param box
	 * @param model
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("selectCalendarList.json")
	public void selectCalendarList(Box box, ModelMap model) throws Exception {
		mainService.selectCalendarList(box, model);
	}
}
