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
 * The Class Sys05Service.
 *
 * @Class Name : Sys05Service.java
 * @Description : Sys05 Service Class
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
public class Sys05Service {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Sys05Service.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 창고 정보 조회
	 * @param paramMap
	 * @exception Exception
	 */
	public void mainList(Box box, ModelMap model) throws Exception {
		List<Box> sysList = sqlSession.selectList("sys.sys05.mainList", box);
		model.put("list", sysList);
	}
	public void roleList(Box box, ModelMap model) throws Exception {
		List<Box> roleList = sqlSession.selectList("sys.sys05.roleList", box);
		List<Box> progList = sqlSession.selectList("sys.sys05.progList", box);
		model.put("role_list", roleList);
		model.put("prog_list", progList);
	}
	
	/**
	 * 공통코드 정보 저장
	 * @param paramMap
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public void mainSave(Box box, ModelMap model) throws Exception {
		
		String loginId = SessionUtil.getUserId(box.getSession());
		
		
		//1. 1건
		Box mstBox = JsonUtil.toObject(box.getString("userData"), Box.class);
		
		String sUserId = mstBox.getString("userId").toString();
		
		//============================
		// Role 데이터 저장
		//============================
		List<Map<String,Object>> roleDatas = JsonUtil.toObject(box.getString("roleDatas"), ArrayList.class);
		String rowState = "";
		for( int i=0; i<roleDatas.size(); i++ ) {
			
			roleDatas.get(i).put("userId",  sUserId);
			roleDatas.get(i).put("loginId", loginId);
			
			rowState = roleDatas.get(i).get("row_state").toString();  // state = created, updated, deleted, createAndDeleted
			
			if( "updated".equals(rowState) ) {
				sqlSession.insert("sys.sys05.insertRoleData", roleDatas.get(i));
			}
		}
		
		//============================
		// Program 데이터 저장
		//============================
		List<Map<String,Object>> progDatas = JsonUtil.toObject(box.getString("progDatas"), ArrayList.class);
		for( int i=0; i<progDatas.size(); i++ ) {
			
			progDatas.get(i).put("userId",  sUserId);
			progDatas.get(i).put("loginId", loginId);
			
			rowState = progDatas.get(i).get("row_state").toString();  // state = created, updated, deleted, createAndDeleted
			
			if( "updated".equals(rowState) ) {
				sqlSession.insert("sys.sys05.insertProgData", progDatas.get(i));
			}
		}
				
		model.put("result", "success");
		
		// 재조회
		Map<String,Object> schData = JsonUtil.toObject(box.getString("schData"), Box.class);  //JSONObject.fromObject(box.getString("cstData"));
		
		Box schBox = new Box();
		for(Map.Entry<String, Object> m : schData.entrySet()) {
			schBox.put(m.getKey(), m.getValue());
		}
		
		List<Box> sysList = sqlSession.selectList("sys.sys05.mainList", schBox);
		model.put("list", sysList);
	}
}

