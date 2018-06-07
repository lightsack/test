package com.fss.fsswms.base.util;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.base.persistence.CmnConstants;

public class ExcelUtil {
	
	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
	
	
	private static final String EXCEL_EXTENSION = ".xls";
	private static final String EXCEL_XEXTENSION = ".xlsx";
	
	public static final boolean isExcelExtension(HttpServletRequest request) throws Exception {
		String uri = HttpUtil.getRequestUri(request);
		
		if(uri.endsWith(EXCEL_EXTENSION) || uri.endsWith(EXCEL_XEXTENSION)) {
			return true;
		}
		return false;
	}
	
	public static final boolean isExcel(HttpServletRequest request) throws Exception {
		return isExcelExtension(request);
	}
	
	public static final void setExcelInfo(ModelMap modelMap, String excelName, List<String[]> columnList, List<?> excelList, String excelSearch, String excelEtc) {
		modelMap.put(CmnConstants.EXCEL_NAME, excelName);
		modelMap.put(CmnConstants.EXCEL_COLUMN, columnList);
		modelMap.put(CmnConstants.EXCEL_LIST, excelList);
		modelMap.put(CmnConstants.EXCEL_SEARCH, excelSearch);
		modelMap.put(CmnConstants.EXCEL_ETC, excelEtc);
	}
	
	public static final void setExcelInfo(ModelMap modelMap, String excelName, List<String[]> columnList, List<?> excelList, String excelSearch) {
		setExcelInfo(modelMap, excelName, columnList, excelList, excelSearch, null);
	}
	
	public static final void setExcelInfo(ModelMap modelMap, String excelName, List<String[]> columnList, List<?> excelList) {
		setExcelInfo(modelMap, excelName, columnList, excelList, null, null);
	}
	
	public static final String createFileName(String fileName, HttpServletRequest request) throws Exception {
        return new StringBuffer(fileName).append("_").append(FormatUtil.now("yyyyMMdd")).append(EXCEL_XEXTENSION).toString();
    }

	public static final CellStyle getTitleStyle(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setBorderTop(CellStyle.BORDER_MEDIUM);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)16);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        return style;
    }
    
	public static final CellStyle getInfoTitleStyle(XSSFWorkbook wb) {
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(new XSSFColor(new Color(215, 215, 215)));
        return style;
    }
    
	public static final CellStyle getInfoDataStyle(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
    
	public static final CellStyle getHeaderStyle(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return style;
    }
	
	
	public static final ArrayList<Box> excelFileToArrayList(MultipartFile[] excelFile) throws Exception {
		ArrayList<Box> list = new ArrayList<Box>();
		
		for (MultipartFile f: excelFile) {
			if (f.isEmpty()) continue;
			FileInputStream inputStream = new FileInputStream(multipartToFile(f));// file에 대한 입력 스트림 생성
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);   // 입력 스트림의 내용으로 xlsx 파일 생성
			int sheetCnt = workbook.getNumberOfSheets();  // sheet 개수를 얻어옴
   
			for(int i = 0; i < sheetCnt; i++){
				XSSFSheet sheet = workbook.getSheetAt(i);  // i번째 sheet 정보를 얻어옴
				int rowCnt = sheet.getPhysicalNumberOfRows(); // row 개수 얻어옴
//				System.out.println("rowCnt >>> " + rowCnt);
				list = new ArrayList<Box>();
				Box cellBox = null;
				for(int r = 1; r < rowCnt; r++){
					
					XSSFRow row = sheet.getRow(r);    // row 정보 얻어옴
					
					if (null != row) {
						int cellCnt = sheet.getRow(0).getPhysicalNumberOfCells(); // cell 개수 얻어옴
	 
						cellBox = new Box();
						for(int c = 0; c < cellCnt; c++){
							
							XSSFCell cell = row.getCell(c);   //cell 정보 얻어옴
							
							if (null != cell) {
//								System.out.println("r >> " + r + " || c >>>> " + c + " >>> "+ cell.getCellType());
								if (0 == cell.getCellType()) {
//									System.out.println("r int >>> " + r + " || c >>> " + c + "  " + cell.getNumericCellValue());
									cellBox.put(Integer.toString(c), cell.getNumericCellValue());
								} else {
//									System.out.println("r String >>> " + r + " || c >>> " + c + "  " + cell.getStringCellValue());
									cellBox.put(Integer.toString(c), cell.getStringCellValue());
								}
							} else {
//								System.out.println("r null >>> " + r + " || c >>> " + c + "  " + "null");
								cellBox.put(Integer.toString(c), "");
							}
							
						}
						
	//					System.out.println("\n");
						list.add(cellBox);
					} else {
						rowCnt = rowCnt + 1;
					}
				}
				
			}
			
		}
		
		return list;
	}
	
	
	//추후 FileUtil로 이동 예정
	public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
	}
	
}
