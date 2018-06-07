package com.fss.fsswms.mvc.classic.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.base.util.ExcelUtil;
import com.fss.fsswms.base.util.JsonUtil;
import com.fss.fsswms.base.util.SessionUtil;

/**
 * The Class Sys03Service.
 *
 * @Class Name : Sys03Service.java
 * @Description : Sys03 Service Class
 * @Modification Information
 * 
 * @author 
 * @version 1.0
 * @see  Copyright (C) by LANDAS All right reserved.
 * @since 2016.12.28
 * @ 
 * @ 수정일                           수정자                    수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2016.12.28   			    최초생성
 */
@Service
public class Sys03Service {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Sys03Service.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 창고 정보 조회
	 * @param paramMap
	 * @exception Exception
	 */
	public void mainList(Box box, ModelMap model) throws Exception {
		List<Box> sysList = sqlSession.selectList("sys.sys03.mainList", box);
		model.put("list", sysList);
	}
	public void pgmList(Box box, ModelMap model) throws Exception {
		List<Box> sysList = sqlSession.selectList("sys.sys03.pgmList", box);
		model.put("list", sysList);
	}
	
	/**
	 * 공통코드 정보 저장
	 * @param paramMap
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public void mainSave(Box box, ModelMap model) throws Exception {
		
		String loginId = SessionUtil.getUserId(box.getSession());
		
		
		//1. 1건 저장 - 1건
		Box mstBox = JsonUtil.toObject(box.getString("mstData"), Box.class);
		
		String rowState = mstBox.getString("row_state").toString();  // state = created, updated, deleted, createAndDeleted
		
		mstBox.put("loginId", loginId);
		
		if( "created".equals(rowState) ) {
			sqlSession.insert("sys.sys03.insertData", mstBox);
		}
		else if( "updated".equals(rowState) ) {
			sqlSession.insert("sys.sys03.updateData", mstBox);
		}
		
		String sRoleCd = mstBox.getString("roleCd").toString();
		
		//============================
		// Detail 데이터 저장
		//============================
		List<Map<String,Object>> pgmDatas = JsonUtil.toObject(box.getString("pgmDatas"), ArrayList.class);
		
		for( int i=0; i<pgmDatas.size(); i++ ) {
			
			pgmDatas.get(i).put("roleCd",  sRoleCd);
			pgmDatas.get(i).put("loginId", loginId);
			
			rowState = pgmDatas.get(i).get("row_state").toString();  // state = created, updated, deleted, createAndDeleted
			
			if( "updated".equals(rowState) ) {
				sqlSession.insert("sys.sys03.insertPgmData", pgmDatas.get(i));
			}
		}
		
		model.put("result", "success");
		
		// 재조회
		Map<String,Object> schData = JsonUtil.toObject(box.getString("schData"), Box.class);  //JSONObject.fromObject(box.getString("cstData"));
		
		Box schBox = new Box();
		for(Map.Entry<String, Object> m : schData.entrySet()) {
			schBox.put(m.getKey(), m.getValue());
		}
		
		List<Box> sysList = sqlSession.selectList("sys.sys03.mainList", schBox);
		model.put("list", sysList);
	}
	
	/**
	 * 창고 삭제
	 * @param paramMap
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public void mainDelete(Box box, ModelMap model) throws Exception {
		
		Box mstBox = JsonUtil.toObject(box.getString("inData"), Box.class);

		sqlSession.delete("sys.sys03.mainDelete", mstBox);
		
		model.put("result", "success");
		
		// 재조회
		Map<String,Object> schData = JsonUtil.toObject(box.getString("schData"), Box.class);  //JSONObject.fromObject(box.getString("cstData"));
		
		Box schBox = new Box();
		for(Map.Entry<String, Object> m : schData.entrySet()) {
			schBox.put(m.getKey(), m.getValue());
		}
		
		List<Box> sysList = sqlSession.selectList("sys.sys03.mainList", schBox);
		model.put("list", sysList);
	}
}

