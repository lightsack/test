package com.fss.fsswms.mvc.classic.std.service;

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
 * The Class Std08Service.
 *
 * @Class Name : Std08Service.java
 * @Description : Std08 Service Class
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
public class Std08Service {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Std08Service.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 창고 정보 조회
	 * @param paramMap
	 * @exception Exception
	 */
	public void mainList(Box box, ModelMap model) throws Exception {
		List<Box> stdList = sqlSession.selectList("std.std08.mainList", box);
		model.put("list", stdList);
	}
	public void detlList(Box box, ModelMap model) throws Exception {
		List<Box> stdList = sqlSession.selectList("std.std08.detlList", box);
		model.put("list", stdList);
	}
	
	/**
	 * 공통코드 정보 저장
	 * @param paramMap
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public void mainSave(Box box, ModelMap model) throws Exception {
		
		String loginId = SessionUtil.getUserId(box.getSession());
		String rowState = "";
		
		//============================
		// Master 데이터 전체저장
		//============================
		List<Map<String,Object>> mstDatas = JsonUtil.toObject(box.getString("mstDatas"), ArrayList.class);  // json 배열을 List<Map>으로 변경
		
		for( int i=0; i<mstDatas.size(); i++ ) {
			
			rowState = mstDatas.get(i).get("row_state").toString();
			
			mstDatas.get(i).put("loginId", loginId);
			
			// state = created, updated, deleted, createAndDeleted
			if( "created".equals(rowState) ) {
				sqlSession.insert("std.std08.insertData", mstDatas.get(i));
			}
			else if( "updated".equals(rowState) ) {
				sqlSession.insert("std.std08.updateData", mstDatas.get(i));
			}
		}
		
		//1. 상품 (기본)정보 저장 - 1건
		Box mstBox = JsonUtil.toObject(box.getString("mstData"), Box.class);
		
		String sLclssCd = mstBox.getString("cateCd").toString();
		
		//============================
		// Detail 데이터 저장
		//============================
		List<Map<String,Object>> dtlDatas = JsonUtil.toObject(box.getString("dtlDatas"), ArrayList.class);
		
		for( int i=0; i<dtlDatas.size(); i++ ) {
			
			rowState = dtlDatas.get(i).get("row_state").toString();
			
			dtlDatas.get(i).put("LClssCd", sLclssCd);
			dtlDatas.get(i).put("loginId", loginId);
			System.out.println("====================");
			System.out.println("======="+i+"========");
			System.out.println("====================");
			// state = created, updated, deleted, createAndDeleted
			if( "created".equals(rowState) ) {
				sqlSession.insert("std.std08.insertDtlData", dtlDatas.get(i));
			}
			else if( "updated".equals(rowState) ) {
				sqlSession.insert("std.std08.updateDtlData", dtlDatas.get(i));
			}
			else if( "deleted".equals(rowState) ) {
				sqlSession.insert("std.std08.deleteDtlData", dtlDatas.get(i));
			}
		}
		
		model.put("result", "success");
		
		// 재조회
		Map<String,Object> schData = JsonUtil.toObject(box.getString("schData"), Box.class);  //JSONObject.fromObject(box.getString("cstData"));
		
		Box schBox = new Box();
		for(Map.Entry<String, Object> m : schData.entrySet()) {
			schBox.put(m.getKey(), m.getValue());
		}
		
		List<Box> stdList = sqlSession.selectList("std.std08.mainList", schBox);
		model.put("list", stdList);
	}
	
	/**
	 * 삭제
	 * @param paramMap
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public void mainDelete(Box box, ModelMap model) throws Exception {
		
		Box mstBox = JsonUtil.toObject(box.getString("inData"), Box.class);

		sqlSession.delete("std.std08.mainDelete", mstBox);
		
		model.put("result", "success");
		
		// 재조회
		Map<String,Object> schData = JsonUtil.toObject(box.getString("schData"), Box.class);  //JSONObject.fromObject(box.getString("cstData"));
		
		Box schBox = new Box();
		for(Map.Entry<String, Object> m : schData.entrySet()) {
			schBox.put(m.getKey(), m.getValue());
		}
		
		List<Box> stdList = sqlSession.selectList("std.std08.mainList", schBox);
		model.put("list", stdList);
	}
}

