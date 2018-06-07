package com.fss.fsswms.base.util;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fss.fsswms.base.persistence.CmnConstants;

public class WebUtil {

	public static void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String browser = getBrowser(request);
		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;
		if ("MSIE".equals(browser)) {
			encodedFilename = URLEncoder.encode(filename, CmnConstants.CHARSET).replaceAll("\\+", "%20");
		} else if ("Firefox".equals(browser)) {
			encodedFilename = "\"" + new String(filename.getBytes(CmnConstants.CHARSET), "8859_1") + "\"";
		} else if ("Opera".equals(browser)) {
			encodedFilename = "\"" + new String(filename.getBytes(CmnConstants.CHARSET), "8859_1") + "\"";
		} else if ("Chrome".equals(browser) || "Safari".equals(browser)) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, CmnConstants.CHARSET));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			throw new IOException("Not supported browser");
		}
		response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);
		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset=" + CmnConstants.CHARSET);
		}
		setFileDownload(response);
	}
	    
	public static String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if(header.indexOf("MSIE")>-1 || header.indexOf("Trident")>-1) {
			return "MSIE";
		} else if(header.indexOf("OPR")>-1 || header.indexOf("Opera")>-1) {
			return "Opera";
		} else if(header.indexOf("Chrome")>-1) {
			return "Chrome";
		} else if(header.indexOf("Safari")>-1) {
			return "Safari";
		} else if(header.indexOf("Firefox")>-1) {
			return "Firefox";
		}
		return null;
	}
	
	public static final void setFileDownload(HttpServletResponse response) {
		response.setHeader("Set-Cookie", "fileDownload=true;path=/");
	}
	
}
