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
 * The Class Std05Service.
 *
 * @Class Name : Wh101Service.java
 * @Description : Wh101 Service Class
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
public class Std05Service {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Std05Service.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 창고 정보 조회
	 * @param paramMap
	 * @exception Exception
	 */
	public void mainList(Box box, ModelMap model) throws Exception {
		List<Box> stdList = sqlSession.selectList("std.std05.mainList", box);
		model.put("list", stdList);
	}
	
	/**
	 * 창고 정보 저장
	 * @param paramMap
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public void saveWhList(Box box, ModelMap model) throws Exception {
		
		//json 배열을 List<Map>으로 변경
		List<Map<String,Object>> gridData = JsonUtil.toObject(box.getString("inData"), ArrayList.class);
		String loginId = SessionUtil.getUserId(box.getSession());;
		String rowState = "";
		
		for( int i=0; i<gridData.size(); i++ ) {
			
			rowState = gridData.get(i).get("row_state").toString();
			
			gridData.get(i).put("loginId", loginId);
			
			// state = created, updated, deleted, createAndDeleted
			if( "created".equals(rowState) ) {
				sqlSession.insert("std.std05.insertData", gridData.get(i));
			}
			else if( "updated".equals(rowState) ) {
				sqlSession.insert("std.std05.updateData", gridData.get(i));
			}
			
			//tempMap.put("inPr", gridData.get(i).get("inPr"));
			// tempMap.put("loginId", loginId);
			
			// sqlSession.insert("std.std05.updateData", tempMap);
		}
		
		model.put("result", "success");
		
		// 재조회
		Map<String,Object> schData = JsonUtil.toObject(box.getString("schData"), Box.class);  //JSONObject.fromObject(box.getString("cstData"));
		
		Box schBox = new Box();
		for(Map.Entry<String, Object> m : schData.entrySet()) {
			schBox.put(m.getKey(), m.getValue());
		}
		
		List<Box> stdList = sqlSession.selectList("std.std05.mainList", schBox);
		model.put("list", stdList);
	}
	
	/**
	 * 창고 삭제
	 * @param paramMap
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteWhList(Box box, ModelMap model) throws Exception {

		Box whBox = JsonUtil.toObject(box.getString("inData"), Box.class);
		
		sqlSession.delete("std.std05.deleteData", whBox);
		
		model.put("result", "success");
		
		// 재조회
		Map<String,Object> schData = JsonUtil.toObject(box.getString("schData"), Box.class);  //JSONObject.fromObject(box.getString("cstData"));
		
		Box schBox = new Box();
		for(Map.Entry<String, Object> m : schData.entrySet()) {
			schBox.put(m.getKey(), m.getValue());
		}
		
		List<Box> stdList = sqlSession.selectList("std.std05.mainList", schBox);
		model.put("list", stdList);
	}
}
