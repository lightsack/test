package com.fss.fsswms.base.util.el;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class CmnConstantsELSupport implements ServletContextAware, InitializingBean {

	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void afterPropertiesSet() throws Exception {
		servletContext.setAttribute("cmnConstants",new CmnConstantsMap());
	}
}
