package com.fss.fsswms.base.util.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.base.util.SessionUtil;

@SuppressWarnings("serial")
public class CodeComboTag extends TagSupport {
	
	private String gbn;
	private String id;
	private String name;
	private Integer size = 1;
	private String optTxt;
	
	public final static String EMPTY_STRING = "";
	public final static String ATTR_TEMPLATE = "%s=\"%s\" ";
	public final static String OPTN_TEMPLATE = "<option value=\"%s\">%s</option>";
	
	public void setGbn(String gbn) {
		this.gbn = gbn;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public void setOptTxt(String optTxt) {
		this.optTxt = optTxt;
	}
	

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("<select ");
			buffer.append((id != null) ? String.format(ATTR_TEMPLATE, "id", id) : String.format(ATTR_TEMPLATE, "id", name));
			buffer.append(String.format(ATTR_TEMPLATE, "name", name));
			buffer.append(String.format(ATTR_TEMPLATE, "size", size.toString()));
			buffer.append(">");
			
			if (optTxt != null) {
				buffer.append(String.format(OPTN_TEMPLATE, "", optTxt));
			}
			
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			List<Box> codeList = SessionUtil.getCodeList(request.getSession());
			for (Box item : codeList) {
				if (gbn.equals(item.getString("gbn"))) {
					buffer.append(String.format(OPTN_TEMPLATE, item.getString("cd"), item.getString("cd")+":"+item.getString("nm")));	
				}
	        }
			
			buffer.append("</select>");
			out.print(buffer.toString());
			
			return SKIP_BODY;
		} catch (Exception e) {
			throw new JspException(e.toString(), e);
		}
	}
	
}