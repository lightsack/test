package com.fss.fsswms.base.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonUtil {
	
	private static ObjectMapper objectMapper;
	private static ObjectMapper objectMapperFormat;

	@Autowired(required=true)
	private JsonUtil(@Qualifier(value="objectMapper") ObjectMapper _objectMapper, @Qualifier(value="objectMapperFormat") ObjectMapper _objectMapperFormat) {
		JsonUtil.objectMapper = _objectMapper;
		JsonUtil.objectMapperFormat = _objectMapperFormat;
	}
	
	public static final boolean isJsonAccept(HttpServletRequest request) throws Exception {
		String accept = request.getHeader("Accept");
		if(accept != null && accept.toLowerCase().startsWith("application/json")) {
			return true;
		}
		return false;
	}

	public static final boolean isJsonBody(HttpServletRequest request) throws Exception {
		String contentType = request.getContentType();
		if(contentType != null && contentType.toLowerCase().startsWith("application/json") ){
			return true;
		}
		return false;
	}

	public static final boolean isJsonExtension(HttpServletRequest request) throws Exception {
		String uri = HttpUtil.getRequestUri(request);
		if(uri.endsWith(".ajax")) {
			return true;
		}
		return false;
	}
	
	public static final boolean isJsonErrorAccept(HttpServletRequest request) throws Exception {
		if("application/json".equals(request.getHeader("X-Error-Accept"))) {
			return true;
		}
		return false;
	}
	
	public static final boolean isJson(HttpServletRequest request) throws Exception {
		if(isJsonExtension(request) || isJsonAccept(request) || isJsonBody(request) || isJsonErrorAccept(request)) {
			return true;
		}
		return false;
	}

	public static final String toJson(Object obj) throws Exception {
		return objectMapper.writeValueAsString(obj);
	}
	
	public static final String toFormatJson(Object obj) throws Exception {
		return objectMapperFormat.writeValueAsString(obj);
	}
	
	public static final <T> T toObject(String json, Class<T> clazz) throws Exception {
		return objectMapper.readValue(json, clazz);
	}
}