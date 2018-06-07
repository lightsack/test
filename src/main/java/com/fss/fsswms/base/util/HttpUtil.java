package com.fss.fsswms.base.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

import com.fss.fsswms.base.data.Box;


public class HttpUtil {

private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	public static String getOrgRequestUri(HttpServletRequest request) throws Exception {
		return new UrlPathHelper().getOriginatingRequestUri(request);
	}
	
	public static String getRequestUri(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String requestUri = getOrgRequestUri(request);
		if(StringUtil.isNotEmpty(contextPath)) {
			requestUri = requestUri.substring(contextPath.length());
		}
		return requestUri;
	}

	public static final boolean isMultipart(HttpServletRequest request) {
		String contentType = request.getContentType();
		if(contentType != null && contentType.toLowerCase().startsWith("multipart/form-data")) {
			return true;
		}
		return false;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if(StringUtil.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			int index = ip.indexOf(',');
			if(index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("Proxy-Client-IP");
		if (StringUtil.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("WL-Proxy-Client-IP");
		if (StringUtil.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("HTTP_CLIENT_IP");
		if (StringUtil.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (StringUtil.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
	public static String getLocalIpAddr() {
		String ip = null;
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			boolean isExit = false;
 			while(en.hasMoreElements()) {
				NetworkInterface ni = en.nextElement();
				if(ni == null || !ni.isUp() || ni.isLoopback() || ni.isVirtual()) {
//				if(ni.isLoopback()) {
					continue;
				}
				Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
				while(inetAddresses.hasMoreElements()) { 
					InetAddress ia = inetAddresses.nextElement();
					if(ia.isLoopbackAddress() || ia.isLinkLocalAddress()) {
						continue;
					}
					if(ia.isSiteLocalAddress()) {
						return ia.getHostAddress();
					}
					if(ia.getHostAddress() != null && ia.getHostAddress().indexOf(".") != -1) {
						ip = ia.getHostAddress();
						isExit = true;
						break;
					}
				}
				if(isExit) {
					break;
				}
			}
			InetAddress inetAddr = InetAddress.getLocalHost();
			if(inetAddr != null) {
				ip = inetAddr.getHostAddress();
			}
		} catch (Exception e) {
			log.error("error : {}", e.getMessage());
		}
		return ip;
	}
	
	public static Map<String, Object> getHeaderToMap(Header[] headers) {
		Map<String, Object> headerMap = null;
		if(headers != null) {
			headerMap = new HashMap<String, Object>();
			String key, value;
			for(Header header : headers) {
				key = header.getName();
				value = header.getValue();
				if(StringUtil.isNotEmpty(value)) {
					headerMap.put(key, value);
				}
			}
		}
		return headerMap;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getHeaderToMap(HttpServletRequest request) {
		Map<String, Object> headerMap = null;
		if(request != null) {
			headerMap = new HashMap<String, Object>();
			Enumeration<String> headerNames = request.getHeaderNames();
			String key, value;
			while(headerNames.hasMoreElements()) {
				key = headerNames.nextElement();
				value = request.getHeader(key);
				if(StringUtil.isNotEmpty(value)) {
					headerMap.put(key, value);
				}
			}
		}
		return headerMap;
	}
	
	public static String getHeaderToJson(Header[] headers) {
		String json = null;
		try {
			Map<String, Object> headerMap = getHeaderToMap(headers);
			if(headerMap == null || headerMap.isEmpty()) {
				return "";
			}
			json = JsonUtil.toFormatJson(headerMap);
		} catch (Exception e) {
			log.error("{}", e);
		}
		return json;
	}
	
	public static String getHeaderToJson(HttpServletRequest request) {
		String json = null;
		try {
			Map<String, Object> headerMap = getHeaderToMap(request);
			if(headerMap == null || headerMap.isEmpty()) {
				return "";
			}
			json = JsonUtil.toFormatJson(headerMap);
		} catch (Exception e) {
			log.error("{}", e);
		}
		return json;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParameterToMap(HttpServletRequest request) {
		Map<String, Object> paramMap = null;
		if(request != null) {
			paramMap = new HashMap<String, Object>();
			Enumeration<String> enumeration = request.getParameterNames();
			String key, value;
			while (enumeration.hasMoreElements()) {
				key = enumeration.nextElement();
				value = request.getParameter(key);
				if(StringUtil.isNotEmpty(value)) {
					paramMap.put(key, value);
				}
			}
		}
		return paramMap;
	}
	
	public static String getParameterToJson(HttpServletRequest request) {
		String json = null;
		try {
			Map<String, Object> paramMap = getParameterToMap(request);
			if(paramMap == null || paramMap.isEmpty()) {
				return "";
			}
			json = JsonUtil.toFormatJson(paramMap);
		} catch (Exception e) {
			log.error("{}", e);
		}
		return json;
	}
	
	public static void setThreadName(Box reqDataBox) {
		if(reqDataBox != null) {
			String threadName = reqDataBox.getString("REQUEST_DATA_THREAD_NAME");
			if(StringUtil.isNotEmpty(threadName)) {
				Thread thread = Thread.currentThread();
				thread.setName(threadName + ">" + thread.getId());
			}
		}
	}
	
	
}