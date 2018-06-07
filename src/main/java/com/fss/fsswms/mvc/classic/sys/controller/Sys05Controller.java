package com.fss.fsswms.mvc.classic.sys.controller;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.mvc.classic.sys.service.Sys05Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class Use05Controller.
 *
 * @Class Name : Use05Controller.java
 * @Description : Use05 Controller Class
 * @Modification Information
 * 
 * @author 
 * @version 1.0
 * @see  Copyright (C) by LANDAS All right reserved.
 * @since 2016.12.28
 * @ 
 * @  수정일                 수정자                    수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2016.12.28               최초생성
 */
@Controller
@RequestMapping("classic/sys/sys05")
public class Sys05Controller {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Sys05Controller.class);

	@Autowired
	private Sys05Service sys05Service;
	
	
	/**
	 * 공통코드정보현황
	 * @param box
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("sys05Form.do")
	public void sys05Form(Box box, ModelMap model) throws Exception {
	}
	
	/**
	 * 공통코드 정보 조회
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("mainList.json")
	public void mainList(Box box, ModelMap model) throws Exception {
		sys05Service.mainList(box, model);
	}
	@RequestMapping("roleList.json")
	public void roleList(Box box, ModelMap model) throws Exception {
		sys05Service.roleList(box, model);
	}
	
	
	/**
	 * 공통코드 정보 저장
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("mainSave.json")
	public void mainSave(Box box, ModelMap model) throws Exception {
		sys05Service.mainSave(box, model);
	}
	
}
