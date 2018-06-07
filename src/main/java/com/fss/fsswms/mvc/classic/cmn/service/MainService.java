package com.fss.fsswms.mvc.classic.cmn.service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.fss.fsswms.base.data.Box;

/**
 * The Class MainService.
 *
 * @Class Name : MainService.java
 * @Description : Main Service Class
 * @Modification Information
 * 
 * @author user01
 * @version 1.0
 * @see  Copyright (C) by LKP All right reserved.
 * @since 2017.07.01
 * @ 
 * @ 수정일                수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2017.07.01   user01      최초생성
 */
@Service
public class MainService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(MainService.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	
	/**
	 * 메인 정보 조회
	 * @param box
	 * @return 
	 * @exception Exception
	 */
	public void selectMain(Box box, ModelMap model) throws Exception {
		
		//게시판_쇼핑정보 목록 조회 (limit 5)
		box.put("boardDiv", "1");
//		model.addAttribute("board1List", sqlSession.selectList("classic.cmn.main.selectBoardList", box));
		
		//게시판_공지사항 목록 조회 (limit 5)
		box.put("boardDiv", "2");
//		model.addAttribute("board2List", sqlSession.selectList("classic.cmn.main.selectBoardList", box));
		
		//게시판_기타 목록 조회 (limit 5)
		box.put("boardDiv", "3");
//		model.addAttribute("board3List", sqlSession.selectList("classic.cmn.main.selectBoardList", box));
				
	}
	
	/**
	 * 달력 정보 조회
	 * @param box
	 * @return 
	 * @exception Exception
	 */
	public void selectCalendarList(Box box, ModelMap model) throws Exception {
//		model.addAttribute("calendarlist", sqlSession.selectList("classic.cmn.main.selectCalendarList", box));
	}
}
