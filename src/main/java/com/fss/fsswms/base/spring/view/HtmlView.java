package com.fss.fsswms.base.spring.view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fss.fsswms.base.util.ExcelUtil;
import com.fss.fsswms.base.util.WebUtil;

public class HtmlView implements View {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(HtmlView.class);
	public static final String DEFAULT_CONTENT_TYPE = "text/html";
	private String contentType = DEFAULT_CONTENT_TYPE;
	private ObjectMapper objectMapper = null;
	private JsonEncoding encoding = JsonEncoding.UTF8;
	private boolean prefixJson = false;
	private Boolean prettyPrint;

	public HtmlView() {
		setContentType(DEFAULT_CONTENT_TYPE);
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		Assert.notNull(objectMapper, "'objectMapper' must not be null");
		this.objectMapper = objectMapper;
		configurePrettyPrint();
	}

	public void setEncoding(JsonEncoding encoding) {
		Assert.notNull(encoding, "'encoding' must not be null");
		this.encoding = encoding;
	}

	public void setPrefixJson(boolean prefixJson) {
		this.prefixJson = prefixJson;
	}

	public void setPrettyPrint(boolean prettyPrint) {
		this.prettyPrint = prettyPrint;
		configurePrettyPrint();
	}

	private void configurePrettyPrint() {
		if (this.prettyPrint != null) {
			this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, this.prettyPrint);
		}
	}

	public void render(Map<String, ?> modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType(getContentType());
		response.setCharacterEncoding(this.encoding.getJavaName());
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
		if(ExcelUtil.isExcel(request) && "MSIE".equals(WebUtil.getBrowser(request))) {
			response.setStatus(HttpStatus.SC_OK);
		}
		if(modelMap != null) {
			Iterator<String> keys = modelMap.keySet().iterator();
			String key = null;
			while(keys.hasNext()) {
				key = keys.next();
				if(key.startsWith(BindingResult.MODEL_KEY_PREFIX)) {
					modelMap.remove(key);
				}
			}
//			log.debug("Result HTML : {}", modelMap);
			writeContent(response.getOutputStream(), modelMap, this.prefixJson);
		}
	}

	protected void writeContent(OutputStream stream, Object value, boolean prefixJson) throws IOException {
		JsonGenerator generator = this.objectMapper.getFactory().createGenerator(stream, this.encoding);
		if (this.objectMapper.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
			generator.useDefaultPrettyPrinter();
		}
		if (prefixJson) {
			generator.writeRaw("{} && ");
		}
		this.objectMapper.writeValue(generator, value);
	}

}
