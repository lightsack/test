package com.fss.fsswms.mvc.classic.sys.controller;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.mvc.classic.sys.service.Sys00Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class Use00Controller.
 *
 * @Class Name : Use00Controller.java
 * @Description : Use00 Controller Class
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
@RequestMapping("classic/sys/sys00")
public class Sys00Controller {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Sys00Controller.class);

	@Autowired
	private Sys00Service sys00Service;
	
	
	/**
	 * 상품정보현황
	 * @param box
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("sys00Form.do")
	public void sys00Form(Box box, ModelMap model) throws Exception {
	}
	
	/**
	 * 상품 정보 조회
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("selectSysList.json")
	public void selectSysList(Box box, ModelMap model) throws Exception {
		sys00Service.selectSysList(box, model);
	}
	@RequestMapping("selectSysDtl.json")
	public void selectSysDtl(Box box, ModelMap model) throws Exception {
		sys00Service.selectSysDtl(box, model);
	}
	
	/**
	 * 상품 정보 저장
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("saveSysList.json")
	public void saveSysList(Box box, ModelMap model) throws Exception {
		sys00Service.saveSysList(box, model);
	}
	
	/**
	 * 상품 정보 엑셀다운로드
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("sysExcel.xls")
	public void sysExcel(Box box, ModelMap model) throws Exception {
		sys00Service.sysExcel(box, model);
	}	
}
