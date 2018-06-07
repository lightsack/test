package com.fss.fsswms.mvc.classic.std.controller;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.mvc.classic.std.service.Std08Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class Use08Controller.
 *
 * @Class Name : Use08Controller.java
 * @Description : Use08 Controller Class
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
@RequestMapping("classic/std/std08")
public class Std08Controller {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Std08Controller.class);

	@Autowired
	private Std08Service std08Service;
	
	
	/**
	 * Category정보현황
	 * @param box
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("std08Form.do")
	public void std08Form(Box box, ModelMap model) throws Exception {
	}
	
	/**
	 * Category 정보 조회
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("mainList.json")
	public void mainList(Box box, ModelMap model) throws Exception {
		std08Service.mainList(box, model);
	}
	@RequestMapping("detlList.json")
	public void detlList(Box box, ModelMap model) throws Exception {
		std08Service.detlList(box, model);
	}
	
	
	/**
	 * Category 정보 저장
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("mainSave.json")
	public void mainSave(Box box, ModelMap model) throws Exception {
		std08Service.mainSave(box, model);
	}
	
	/**
	 * Category 삭제
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("mainDelete.json")
	public void mainDelete(Box box, ModelMap model) throws Exception {
		std08Service.mainDelete(box, model);
	}
	
}
