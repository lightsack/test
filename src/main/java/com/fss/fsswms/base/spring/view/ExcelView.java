package com.fss.fsswms.base.spring.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.base.persistence.CmnConstants;
import com.fss.fsswms.base.util.ExcelUtil;
import com.fss.fsswms.base.util.NumberUtil;
import com.fss.fsswms.base.util.StringUtil;
import com.fss.fsswms.base.util.WebUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelView extends AbstractExcelView {
	
	private static final Logger log = LoggerFactory.getLogger(ExcelView.class);
	@SuppressWarnings("unused")
	private static final String LIST_COUNT_NAME = "총건수";
	@SuppressWarnings("unused")
	private static final String SEARCH_TITLE_NAME = "검색조건";
	
    @SuppressWarnings("unchecked")
	@Override
    protected void buildExcelDocument(Map<String, Object> modelMap, XSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	log.info(" ExcelView >>>>>>>>>>>>>>>>>>>> ");
    	
    	String excelName = (String)modelMap.get(CmnConstants.EXCEL_NAME);
    	if(StringUtil.isEmpty(excelName)) {
    		excelName = "excel";
    	}
    	List<String[]> columnList = (List<String[]>)modelMap.get(CmnConstants.EXCEL_COLUMN);
    	int columnSize = -1;
    	if(columnList == null || (columnSize = columnList.size()) < 1) {
    		throw new Exception("Excel Column 정의가 되지 않았습니다.");
    	}
    	List<Box> excelList = (List<Box>)modelMap.get(CmnConstants.EXCEL_LIST);
    	if(excelList == null) {
    		throw new Exception("Excel List 정의가 되지 않았습니다.");
    	}
    	int excelSize = excelList.size();
    	if(excelSize > 10000) {
    		throw new Exception("Excel Export 허용건수가 초과하였습니다.");
    	}
    	
    	log.info(" ExcelView 222 >>>>>>>>>>>>>>>>>>>> ");
    	
    	
    	int rowIdx = 0;
    	int cellIdx = 0;
    	Sheet sheet = workbook.createSheet(excelName);
    	Row row = null;
    	Cell cell = null;
    	
    	CellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);
    	row = sheet.createRow(rowIdx++);
        for (int i = 0; i < columnSize; i++) {
            cell = row.createCell(cellIdx+i);
            cell.setCellValue(columnList.get(i)[1]);
            cell.setCellStyle(headerStyle);
        }
        
        Box data = null;
        Object colValObj = null;
        for (int i = 0; i < excelSize; i++) {
            data = (Box)excelList.get(i);
            row = sheet.createRow(rowIdx++);
            for (int j = 0; j < columnSize; j++) {
                cell = row.createCell(cellIdx + j);
                colValObj = data.get(columnList.get(j)[0]);
                if (colValObj instanceof BigDecimal) {
                	cell.setCellValue(NumberUtil.toBigDecimal(colValObj).doubleValue());
                } else {
                    if (colValObj != null) {
                    	cell.setCellValue(String.valueOf(colValObj));
                    }
                }
            }
        }
        
        log.info(" ExcelView 333 >>>>>>>>>>>>>>>>>>>> " + columnSize);
        
/*        
        for (int i = 0; i < columnSize; i++) {
        	log.info(" ExcelView ["+i+"] >>>>>>>>>>>>>>>>>>>> " + i);
        	sheet.autoSizeColumn(2);
//        	sheet.autoSizeColumn(i);
        }
*/    	
        
        log.info(" ExcelView 444 >>>>>>>>>>>>>>>>>>>> " +excelName + " ||| " );
         
        
        String fileName = ExcelUtil.createFileName(excelName, request);
        
        log.info(" ExcelView 555 >>>>>>>>>>>>>>>>>>>> " + excelName + " ||| " );
        
        WebUtil.setDisposition(fileName, request, response);
        
        log.info(" ExcelView 66666666 >>>>>>>>>>>>>>>>>>>> " + excelName + " ||| " );
    }
    
}
