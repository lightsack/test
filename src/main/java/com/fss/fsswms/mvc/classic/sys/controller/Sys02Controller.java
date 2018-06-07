package com.fss.fsswms.mvc.classic.sys.controller;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.mvc.classic.sys.service.Sys02Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class Use02Controller.
 *
 * @Class Name : Use02Controller.java
 * @Description : Use02 Controller Class
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
@RequestMapping("classic/sys/sys02")
public class Sys02Controller {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Sys02Controller.class);

	@Autowired
	private Sys02Service sys02Service;
	
	
	/**
	 * 창고정보현황
	 * @param box
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("sys02Form.do")
	public void sys02Form(Box box, ModelMap model) throws Exception {
	}
	
	/**
	 * 창고 정보 조회
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("mainList.json")
	public void mainList(Box box, ModelMap model) throws Exception {
		sys02Service.mainList(box, model);
	}
	
	/**
	 * 창고 정보 저장
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("mainSave.json")
	public void mainSave(Box box, ModelMap model) throws Exception {
		sys02Service.mainSave(box, model);
	}
	
	/**
	 * 창고 삭제
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("mainDelete.json")
	public void mainDelete(Box box, ModelMap model) throws Exception {
		sys02Service.mainDelete(box, model);
	}
	
}
