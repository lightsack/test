package com.fss.fsswms.mvc.classic.sys.controller;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.mvc.classic.sys.service.Sys01Service;

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
@RequestMapping("classic/sys/sys01")
public class Sys01Controller {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Sys01Controller.class);

	@Autowired
	private Sys01Service sys01Service;
	
	
	/**
	 * 공통코드정보현황
	 * @param box
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("sys01Form.do")
	public void sys01Form(Box box, ModelMap model) throws Exception {
	}
	
	/**
	 * 공통코드 정보 조회
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("selectMstList.json")
	public void selectMstList(Box box, ModelMap model) throws Exception {
		sys01Service.selectMstList(box, model);
	}
	@RequestMapping("selectDtlList.json")
	public void selectDtlList(Box box, ModelMap model) throws Exception {
		sys01Service.selectDtlList(box, model);
	}
	
	
	/**
	 * 공통코드 정보 저장
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("saveMst.json")
	public void saveMst(Box box, ModelMap model) throws Exception {
		sys01Service.saveMst(box, model);
	}
	
	/**
	 * 공통코드 삭제
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("deleteMst.json")
	public void deleteMst(Box box, ModelMap model) throws Exception {
		sys01Service.deleteMst(box, model);
	}
	
}
