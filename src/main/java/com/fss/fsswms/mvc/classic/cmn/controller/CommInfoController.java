package com.fss.fsswms.mvc.classic.cmn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.mvc.classic.cmn.service.CommInfoService;

/**
 * The Class CommInfoController.
 *
 * @Class Name : CommInfoController.java
 * @Description : 공통정보 Controller Class
 * @Modification Information
 * 
 * @author 
 * @version 1.0
 * @see  Copyright (C) by kdmes All right reserved.
 * @since 2016.11.14
 * @ 
 * @  수정일                 수정자                    수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2016.11.14   user03      최초생성
 */
@Controller
@RequestMapping("classic/cmn/commInfo")
public class CommInfoController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CommInfoController.class);
	
	/** CommInfoService */
	@Autowired
	private CommInfoService commInfoService;

	/**
	 * 팝업확인 정보 조회
	 * 
	 * @param paramMap
	 * @param model
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("selectPopChkList.json")
	public void selectPopChkList(Box box, ModelMap model) throws Exception {
		commInfoService.selectPopChkList(box, model);
	}
}