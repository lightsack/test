package com.fss.fsswms.base.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileIO {

	public static final String ORIGINAL_FILE_NAME = "fileNameOri";
	public static final String FILE_EXT = "fileNameExt";
	public static final String FILE_CONTENT_TYPE = "fileContentType";
	public static final String SAVED_FILE_NAME = "fileNameToSave";
	public static final String SAVED_FILE_SIZE = "fileSizeToSave";
	public static final String SAVED_FILE_PATH = "filePathToSave";
	
	public Map<String, Object> saveFile(MultipartFile file, HttpServletRequest request) throws Exception{
		
		String fileNameOri = file.getOriginalFilename();
		String fileSize = file.getSize()+"";
		String fileContentType = file.getContentType();
		String fileNameExt = fileNameOri.lastIndexOf(".") != -1 ? fileNameOri.substring(fileNameOri.lastIndexOf(".")+1).toLowerCase() : "";
		String filePathToSave = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/") + "upload/";
		
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
		String fileNm = dayTime.format(new Date(time)); 
		
		String fileNameToSave = fileNm +"."+ fileNameExt;
		
		File dir = new File(filePathToSave);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		
		File uploadFile = new File(filePathToSave, fileNameToSave);		
//		if(uploadFile.mkdirs() == false){
//			throw new AuthenticationException("exception!");
//		};
		
		if (uploadFile.exists()) {
			uploadFile.delete();
		}
		
		file.transferTo(uploadFile);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(ORIGINAL_FILE_NAME, fileNameOri);
		resultMap.put(FILE_EXT, fileNameExt);
		resultMap.put(FILE_CONTENT_TYPE, fileContentType);
		resultMap.put(SAVED_FILE_NAME, fileNameToSave);
		resultMap.put(SAVED_FILE_SIZE, fileSize);
		resultMap.put(SAVED_FILE_PATH, filePathToSave);
		
		return resultMap;
	}
	
	
	public Map<String, Object> removeFile(String filePath, HttpServletRequest request) throws Exception{
//		File removeFile = new File(request.getSession().getServletContext().getRealPath("/")+filePath);		
		File removeFile = new File(filePath);
		if (removeFile.exists()) {
			removeFile.delete();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		return resultMap;
	}
	
	
}
