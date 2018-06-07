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

/**
 * The Class Sys002Service.
 *
 * @Class Name : Sys002Service.java
 * @Description : Sys002 Service Class
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
public class Sys00Service {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Sys00Service.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	
	/**
	 * 상품 정보 조회
	 * @param paramMap
	 * @exception Exception
	 */
	public void selectSysList(Box box, ModelMap model) throws Exception {
		//기본 정렬 조건 셋팅
		if ("".equals(box.nvl("sortColumn"))) {
		   box.put("sortColumn", "SORT, GOD_CD");
		   box.put("sortOrder", "ASC");		//오름차순
		}
		List<Box> sysList = sqlSession.selectList("sys.sys002.selectSysList", box);
		model.put("list", sysList);
	}
	public void selectSysDtl(Box box, ModelMap model) throws Exception {
		
		List<Box> sysDtl = sqlSession.selectList("sys.sys002.selectSysDtl", box);
		model.put("list", sysDtl);
	}
	
	/**
	 * 상품 정보 저장
	 * @param paramMap
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public void saveSysList(Box box, ModelMap model) throws Exception {
		
		//json 배열을 List<Map>으로 변경
		List<Map<String,Object>> gridData = JsonUtil.toObject(box.getString("gridData"), ArrayList.class);
		String loginId = (String)box.getString("loginId");
		
		for (int i = 0; i < gridData.size(); i++) {
			System.out.println("===================================================");
			System.out.println("===================================================");
			System.out.println("===================================================");
			System.out.println(gridData.get(i));
			System.out.println("===================================================");
			System.out.println("===================================================");
			System.out.println("===================================================");
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("sysCd",    gridData.get(i).get("sysCd"));
			tempMap.put("sysNm",    gridData.get(i).get("sysNm"));
			tempMap.put("sort",     gridData.get(i).get("sort"));			
			tempMap.put("maker",    gridData.get(i).get("maker"));
			tempMap.put("origin",   gridData.get(i).get("origin"));
			tempMap.put("outshell", gridData.get(i).get("outshell"));
			tempMap.put("inshell",  gridData.get(i).get("inshell"));
			tempMap.put("filler",   gridData.get(i).get("filler"));
			tempMap.put("sysMoq",   gridData.get(i).get("sysMoq"));
			tempMap.put("crMoq",    gridData.get(i).get("crMoq"));
			
			//tempMap.put("inPr", gridData.get(i).get("inPr"));
			tempMap.put("loginId", loginId);
			
			sqlSession.insert("sys.sys002.updateData", tempMap);
		}
		
		model.put("result", "success");
		List<Box> sysList = sqlSession.selectList("sys.sys002.selectSysList", box);
		model.put("list", sysList);
	}
	
	/**
	 * 상품 정보 엑셀다운로드
	 * @param paramMap
	 * @exception Exception
	 */
	public void sysExcel(Box box, ModelMap model) throws Exception {
		//기본 정렬 조건 셋팅
		if ("".equals(box.nvl("sortColumn"))) {
		   box.put("sortColumn", "SORT, GOD_CD");	
		   box.put("sortOrder", "ASC");		//오름차순
		}
		List<?> sysList = sqlSession.selectList("sys.sys002.selectSysList", box);

		//컬럼명 셋팅
		List<String[]> columnList = new ArrayList<String[]>();
		columnList.add(new String[]{"sysCd", "상품코드"});
		columnList.add(new String[]{"sysNm", "상품명"});
		columnList.add(new String[]{"cstNm", "상품구분"});
		columnList.add(new String[]{"szPtNm", "사이즈패턴"});		
		columnList.add(new String[]{"sort", "순서"});
		//columnList.add(new String[]{"inPr", "원가"});		
		//columnList.add(new String[]{"cstNm", "매입처명"});
		columnList.add(new String[]{"maker", "제조사"});
		columnList.add(new String[]{"origin", "원산지"});
		columnList.add(new String[]{"outshell", "겉감"});
		columnList.add(new String[]{"inshell", "안감"});
		columnList.add(new String[]{"filler", "충전제"});
		columnList.add(new String[]{"sysMoq", "상품최소생산수량"});
		columnList.add(new String[]{"crMoq", "색상최소생산수량"});
		columnList.add(new String[]{"locNo", "로케이션"});
				
		//Excel Download
		ExcelUtil.setExcelInfo(model, "상품정보", columnList, sysList);
	}	
}
