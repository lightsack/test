package com.fss.fsswms.mvc.classic.std.controller;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.mvc.classic.std.service.Std05Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class Use01Controller.
 *
 * @Class Name : Use01Controller.java
 * @Description : Use01 Controller Class
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
@RequestMapping("classic/std/std05")
public class Std05Controller {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Std05Controller.class);

	@Autowired
	private Std05Service std05Service;
	
	
	/**
	 * 창고정보현황
	 * @param box
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("std05Form.do")
	public void std05Form(Box box, ModelMap model) throws Exception {
	}
	
	/**
	 * 창고 정보 조회
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("mainList.json")
	public void mainList(Box box, ModelMap model) throws Exception {
		std05Service.mainList(box, model);
	}
	
	/**
	 * 창고 정보 저장
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("saveWhList.json")
	public void saveWhList(Box box, ModelMap model) throws Exception {
		std05Service.saveWhList(box, model);
	}
	
	/**
	 * 창고 삭제
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("deleteWhList.json")
	public void deleteWhList(Box box, ModelMap model) throws Exception {
		std05Service.deleteWhList(box, model);
	}
	
}
