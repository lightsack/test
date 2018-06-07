package com.fss.fsswms.mvc.classic.cmn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.mvc.classic.cmn.service.PopupService;


/**
 * The Class PopupController.
 *
 * @Class Name : PopupController.java
 * @Description : CommonPopup Controller Class
 * @Modification Information
 * 
 * @author 
 * @version 1.0
 * @see  Copyright (C) by LKP All right reserved.
 * @since 2017.08.21
 * @ 
 * @  수정일                 수정자                    수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2017.08.21  user01      최초생성
 */
@Controller
@RequestMapping("classic/cmn/popup")
public class PopupController {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(PopupController.class);
	
	@Autowired
	private PopupService popupService;
	
	/**
	 * 엑셀업로드 팝업
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("excelUploadPopup.do")
	public void excelUploadPopup(Box box, ModelMap model) throws Exception {
		model.put("progId", box.nvl("progId"));
		model.put("travAgenCd", box.nvl("travAgenCd"));
		model.put("boxNo", box.nvl("boxNo"));
	}
	
	/**
	 * 엑셀업로드
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("excelUpload.json")
	public void excelUpload(Box box, ModelMap model, @RequestParam("excelFile") MultipartFile[] excelFile) throws Exception {
		popupService.excelUpload(box, model, excelFile);
	}
	
	/**
	 * 고객판매정산 새창 팝업
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("custSalePopup.do")
	public String custSalePopup(Box box, ModelMap model) throws Exception {
		model.put("psptNo", box.getString("psptNo"));
		model.put("calDt", box.getString("calDt"));
		model.put("travAgenCd", box.getString("travAgenCd"));
		model.put("calSeq", box.getString("calSeq"));
		model.put("boxNo", box.getString("boxNo"));
		System.out.println(box.getString("psptNo")+" / "+box.getString("calDt")+" / "+box.getString("travAgenCd")+" / "+box.getString("calSeq")+" / "+box.getString("boxNo"));
		return "noframe:/classic/cmn/popup/custSalePopup";
	}
	
	/**
	 * 고객 판매정산상세데이터 조회
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("selectCustSale.json")
	public void selectCustSale(Box box, ModelMap model) throws Exception {
		popupService.selectCustSale(box, model);
	}
	
	/**
	 * 고객 판매정산데이터 조회
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("selectCustSaleCal.json")
	public void selectCustSaleCal(Box box, ModelMap model) throws Exception {
		popupService.selectCustSaleCal(box, model);
	}
	
	
	/**
	 * 게시판 상세 팝업
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("boardViewPopup.do")
	public void boardViewPopup(Box box, ModelMap model) throws Exception {
		popupService.selectBoard(box, model);
		model.put("boardDiv", box.getString("boardDiv"));
	}
	
	/**
	 * 여행사정보 팝업
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("travAgenInfoPopup.do")
	public void travAgenInfoPopup(Box box, ModelMap model) throws Exception {
		model.put("travAgenCdorNm", box.getString("travAgenCdorNm"));
	}
	
	/**
	 * 여행사 목록 조회
	 * @param box
	 * @param model
	 * @exception Exception
	 */
	@RequestMapping("selectTravAgenList.json")
	public void selectTravAgenList(Box box, ModelMap model) throws Exception {
		popupService.selectTravAgenList(box, model);
	}
}
