package com.fss.fsswms.mvc.classic.cmn.service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.base.util.SessionUtil;

/**
 * The Class CommInfoService.
 *
 * @Class Name : CommInfoService.java
 * @Description : 공통정보 Service Class
 * @Modification Information
 * 
 * @author user03
 * @version 1.0
 * @see  Copyright (C) by kdmes All right reserved.
 * @since 2016.11.14
 * @ 
 * @ 수정일                           수정자                    수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2016.11.14   user03      최초생성
 */
@Service
public class CommInfoService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CommInfoService.class);
	
	@Autowired
	private SqlSession sqlSession;

	/**
	 * 팝업확인 정보 조회
	 * 
	 * @param paramMap
	 * @return selectPopChkList 쿼리 결과
	 * @exception Exception
	 */
	public void selectPopChkList(Box box, ModelMap model) throws Exception {
		model.addAttribute("popChkList", sqlSession.selectList("classic.cmn.commInfo.selectPopChkList", box));
	}
}