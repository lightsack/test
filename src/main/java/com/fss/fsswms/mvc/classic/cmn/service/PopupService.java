package com.fss.fsswms.mvc.classic.cmn.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.base.util.ExcelUtil;
import com.fss.fsswms.base.util.JsonUtil;
import com.fss.fsswms.base.util.SessionUtil;


/**
 * The Class PopupService.
 *
 * @Class Name : PopupService.java
 * @Description : CommonPopup Service Class
 * @Modification Information
 * 
 * @author user01
 * @version 1.0
 * @see  Copyright (C) by LKP All right reserved.
 * @since 2017.01.06
 * @ 
 * @ 수정일                           수정자                    수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2017.01.06   			    최초생성
 */
@Service
public class PopupService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(PopupService.class);
	
	@Autowired
	private SqlSession sqlSession;
		
	/**
	 * 엑셀업로드
	 * @param box
	 * @return 
	 * @exception Exception
	 */
	public void excelUpload(Box box, ModelMap model, MultipartFile[] excelFile) throws Exception {
		box.put("userId", SessionUtil.getUserId(box.getSession()));
		
		//엑셀 파일을 읽어 ArrayList로 변환
		ArrayList<Box> excelDataList = ExcelUtil.excelFileToArrayList(excelFile);
		
		//상품별 고객관리
		if ("god01".equals(box.getString("progId"))) {
			ArrayList<Box> insertDataList = new ArrayList<Box>();
			Box insertDataBox = null;
			for (int i = 0; i < excelDataList.size(); i++) {
				insertDataBox = new Box();
				insertDataBox.put("str1", excelDataList.get(i).get("0"));
				insertDataBox.put("str2", excelDataList.get(i).get("1"));
				insertDataBox.put("str3", excelDataList.get(i).get("2"));
				insertDataBox.put("str4", excelDataList.get(i).get("3"));
				insertDataBox.put("num1", excelDataList.get(i).getDouble("4"));
				insertDataBox.put("num2", excelDataList.get(i).getDouble("5"));
				insertDataBox.put("num3", excelDataList.get(i).getDouble("6"));
				insertDataBox.put("num4", excelDataList.get(i).getDouble("7"));
				insertDataBox.put("num5", excelDataList.get(i).getDouble("8"));
				insertDataBox.put("num6", excelDataList.get(i).getDouble("9"));
				insertDataBox.put("num7", excelDataList.get(i).getDouble("10"));
				insertDataBox.put("num8", excelDataList.get(i).getDouble("11"));
				insertDataBox.put("num9", excelDataList.get(i).getDouble("12"));
				insertDataBox.put("num10", excelDataList.get(i).getDouble("13"));
				insertDataBox.put("num11", excelDataList.get(i).getDouble("14"));
				insertDataBox.put("num12", excelDataList.get(i).getDouble("15"));
				insertDataBox.put("num13", excelDataList.get(i).getDouble("16"));
				insertDataBox.put("num14", excelDataList.get(i).getDouble("17"));
				insertDataBox.put("num15", excelDataList.get(i).getDouble("18"));
				insertDataBox.put("num16", excelDataList.get(i).getDouble("19"));
				insertDataBox.put("num17", excelDataList.get(i).getDouble("20"));
				insertDataBox.put("num18", excelDataList.get(i).getDouble("21"));
				insertDataBox.put("num19", excelDataList.get(i).getDouble("22"));
				insertDataBox.put("num20", excelDataList.get(i).getDouble("23"));
				insertDataBox.put("num21", excelDataList.get(i).getDouble("24"));				
				insertDataBox.put("num22", excelDataList.get(i).getDouble("25"));
				insertDataBox.put("num23", excelDataList.get(i).getDouble("26"));
				insertDataBox.put("num24", excelDataList.get(i).getDouble("27"));
				insertDataBox.put("num25", excelDataList.get(i).getDouble("28"));
				insertDataBox.put("num26", excelDataList.get(i).getDouble("29"));
				insertDataBox.put("num27", excelDataList.get(i).getDouble("30"));
				insertDataBox.put("num28", excelDataList.get(i).getDouble("31"));
				insertDataBox.put("num29", excelDataList.get(i).getDouble("32"));
				insertDataBox.put("num30", excelDataList.get(i).getDouble("33"));
				insertDataBox.put("num31", excelDataList.get(i).getDouble("34"));
				insertDataBox.put("num32", excelDataList.get(i).getDouble("35"));
				insertDataBox.put("num33", excelDataList.get(i).getDouble("36"));				
				insertDataList.add(insertDataBox);
			}

			//TB_EXCEL_UPLOAD 다건 등록
			if (0 < insertDataList.size()) {
				box.put("insertDataList", insertDataList);
				sqlSession.insert("classic.cmn.popup.insertExcelUploadList", box);	
			}

			//상품별단가 그리드에 뿌려줄 데이터 조회(TB_EXCEL_UPLOAD, TB_GOD outor join)
			model.addAttribute("list", sqlSession.selectList("classic.cmn.popup.selectGodCustList", box));
		} else if ("god02".equals(box.getString("progId"))) { //상품별 그룹코드 업로드
			ArrayList<Box> insertDataList = new ArrayList<Box>();
			Box insertDataBox = null;

			for (int i = 0; i < excelDataList.size(); i++) {
				insertDataBox = new Box();
				insertDataBox.put("str1", excelDataList.get(i).get("0"));
				insertDataBox.put("str2", excelDataList.get(i).get("1"));
				insertDataBox.put("str3", excelDataList.get(i).get("2"));
				insertDataBox.put("str4", excelDataList.get(i).get("3"));
				insertDataBox.put("str5", excelDataList.get(i).get("4"));
				insertDataBox.put("str6", excelDataList.get(i).get("5"));
				insertDataBox.put("str7", excelDataList.get(i).get("6"));
				insertDataBox.put("str8", excelDataList.get(i).get("7"));
				insertDataBox.put("str9", excelDataList.get(i).get("8"));
				insertDataBox.put("str10", excelDataList.get(i).get("9"));
				insertDataBox.put("str11", excelDataList.get(i).get("10"));
				insertDataBox.put("str12", excelDataList.get(i).get("11"));
				insertDataList.add(insertDataBox);
			}

			//TB_EXCEL_UPLOAD 다건 등록
			if (0 < insertDataList.size()) {
				box.put("insertDataList", insertDataList);
				sqlSession.insert("classic.cmn.popup.insertExcelUploadList", box);	
			}

			//상품별 그룹코드
			model.addAttribute("list", sqlSession.selectList("classic.cmn.popup.selectGodGrupList", box));
		} else if ("sal03".equals(box.getString("progId"))) { //면세점별 매출 업로드
			ArrayList<Box> insertDataList = new ArrayList<Box>();
			Box insertDataBox = null;
			for (int i = 1; i < excelDataList.size(); i++) {
				insertDataBox = new Box();
				insertDataBox.put("progId", box.getString("progId"));				//
				insertDataBox.put("userId", box.getString("userId"));				//		
				
				insertDataBox.put("str1", excelDataList.get(i).get("1"));			//구매일자
				insertDataBox.put("str2", excelDataList.get(i).get("2"));			//면세점코드
				insertDataBox.put("str3", excelDataList.get(i).get("3"));			//그룹코드
				insertDataBox.put("str4", excelDataList.get(i).get("4"));			//여권번호
				insertDataBox.put("str5", excelDataList.get(i).get("5"));			//고객명
				insertDataBox.put("str6", excelDataList.get(i).get("12"));			//정산일자
				
				insertDataBox.put("str7", excelDataList.get(i).get("0"));			//정산순번
				
				insertDataBox.put("num1", excelDataList.get(i).getDouble("6"));		//수입_구매금액
				insertDataBox.put("num2", excelDataList.get(i).getDouble("7"));		//수입_수수료율
				insertDataBox.put("num3", excelDataList.get(i).getDouble("8"));		//국산_구매금액
				insertDataBox.put("num4", excelDataList.get(i).getDouble("9"));		//국산_수수료율
				insertDataBox.put("num5", excelDataList.get(i).getDouble("10"));		//LV_구매금액
				insertDataBox.put("num6", excelDataList.get(i).getDouble("11"));	//LV_수수료율
				insertDataBox.put("num7", excelDataList.get(i).getDouble("13"));	//환율
				
				sqlSession.insert("classic.cmn.popup.insertExcelUpload", insertDataBox);
				//insertDataList.add(insertDataBox);
			}

			//TB_EXCEL_UPLOAD 다건 등록
			/*
			if (0 < insertDataList.size()) {
				box.put("insertDataList", insertDataList);
				sqlSession.insert("classic.cmn.popup.insertExcelUploadList", box);	
			}
			*/

			//여행사별 매출
			model.addAttribute("list", sqlSession.selectList("classic.cmn.popup.selectSaleList", box));
		}  else if ("sal03Batch".equals(box.getString("progId"))) { //면세점별 매출 업로드
			ArrayList<Box> insertDataList = new ArrayList<Box>();
			Box insertDataBox = null;
			for (int i = 1; i < excelDataList.size(); i++) {
				insertDataBox = new Box();
				insertDataBox.put("progId", box.getString("progId"));				//
				insertDataBox.put("userId", box.getString("userId"));				//
				insertDataBox.put("str1", excelDataList.get(i).get("1"));			//구매일자
				insertDataBox.put("str2", excelDataList.get(i).get("2"));			//면세점코드
				insertDataBox.put("str3", excelDataList.get(i).get("3"));			//그룹코드
				insertDataBox.put("str4", excelDataList.get(i).get("4"));			//여권번호
				insertDataBox.put("str5", excelDataList.get(i).get("5"));			//고객명
				insertDataBox.put("str6", excelDataList.get(i).get("12"));			//정산일자
				
				insertDataBox.put("str7", excelDataList.get(i).get("0"));			//정산순번
				
				insertDataBox.put("num1", excelDataList.get(i).getDouble("6"));		//수입_구매금액
				insertDataBox.put("num2", excelDataList.get(i).getDouble("7"));		//수입_수수료율
				insertDataBox.put("num3", excelDataList.get(i).getDouble("8"));		//국산_구매금액
				insertDataBox.put("num4", excelDataList.get(i).getDouble("9"));		//국산_수수료율
				insertDataBox.put("num5", excelDataList.get(i).getDouble("10"));		//LV_구매금액
				insertDataBox.put("num6", excelDataList.get(i).getDouble("11"));	//LV_수수료율
				insertDataBox.put("num7", excelDataList.get(i).getDouble("13"));	//환율
				
				sqlSession.insert("classic.cmn.popup.insertExcelUpload", insertDataBox);
			}
			
			sqlSession.insert("classic.cmn.popup.callSpTbSaleInsert", box);
		}
		
		//TB_EXCEL_UPLOAD 삭제
		sqlSession.delete("classic.cmn.popup.deleteExcelUpload", box);
	}
	
	/**
	 * 고객 판매정산상세데이터 조회
	 * @param box
	 * @return 
	 * @exception Exception
	 */
	public void selectCustSale(Box box, ModelMap model) throws Exception {
		Map<String,Object> searchData = JsonUtil.toObject(box.getString("searchData"), Box.class);
		
		Box searchBox = new Box();
		for(Map.Entry<String, Object> m : searchData.entrySet()) {
			searchBox.put(m.getKey(), m.getValue());
		}
		
		model.addAttribute("custSaleList", sqlSession.selectList("classic.cmn.popup.selectCustSale", searchBox));
	}
	
	/**
	 * 고객 판매정산데이터 조회
	 * @param box
	 * @return 
	 * @exception Exception
	 */
	public void selectCustSaleCal(Box box, ModelMap model) throws Exception {
		Map<String,Object> searchData = JsonUtil.toObject(box.getString("searchData"), Box.class);
		
		Box searchBox = new Box();
		for(Map.Entry<String, Object> m : searchData.entrySet()) {
			searchBox.put(m.getKey(), m.getValue());
		}
		
		model.addAttribute("custSaleCalList", sqlSession.selectList("classic.cmn.popup.selectCustSaleCal", searchBox));
	}
	
	
	/**
	 * 게시판 정보 조회
	 * @param box
	 * @return 
	 * @exception Exception
	 */
	public void selectBoard(Box box, ModelMap model) throws Exception {
		Box rstBox = sqlSession.selectOne("classic.cmn.popup.selectBoard", box);
		rstBox.put("content", rstBox.getString("content").replaceAll("\r\n", "<br/>"));

		model.addAttribute("boardInfo", rstBox);
	}
	
	/**
	 * 여행사 목록 조회
	 * @param box
	 * @return 
	 * @exception Exception
	 */
	public void selectTravAgenList(Box box, ModelMap model) throws Exception {
		if ("".equals(box.nvl("sortColumn"))) {
			box.put("sortColumn", "TRAV_AGEN_CD");	
			box.put("sortOrder", "ASC");
		}
		model.addAttribute("list", sqlSession.selectList("classic.cmn.popup.selectTravAgenList", box));
	}
	
}
