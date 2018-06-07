package com.fss.fsswms.base.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.fss.fsswms.base.util.FormatUtil;
import com.fss.fsswms.base.util.MapUtil;
import com.fss.fsswms.base.util.StringUtil;

public class Box extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = -1275868958044369838L;
	private HttpSession session;

	public Box() {
		super();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Box(Map map) {
		super(map);
	}

	public Box(int initialCapacity, float loadFactor) {
    	super(initialCapacity, loadFactor);
    }

    public Box(int initialCapacity) {
    	super(initialCapacity);
    }

    public String getString(String key) {
		return MapUtil.getString(this, key);
	}

	public int getInt(String key) {
		return MapUtil.getInt(this, key);
	}

	public double getDouble(String key) {
		return MapUtil.getDouble(this, key);
	}

	public BigDecimal getBigDecimal(String key) {
		return MapUtil.getBigDecimal(this, key);
	}

	/**
	 * Date 값을 반환하며 값이 없는 경우는 null을 반환한다.
	 * @param key
	 * @return
	 */
	public Date getDate(String key) {
		Object src = this.get(key);
		if (src instanceof Date) {
			return (Date)src;
		} else {
			return null;
		}
	}

	/**
	 * 값이 Date객체인 경우 dateFormat대로 변환된다.
	 * @param key
	 * @return
	 */
	public String getDateStr(String key, String dateFormat) {
		Object src = this.get(key);
		if (src instanceof Date) {
			return FormatUtil.formatDate((Date)src, dateFormat);
		} else {
			return null;
		}
	}

	@Override
	public Box clone() {
		return (Box)super.clone();
	}

	public String getParameter(String name) {
		Object value = get(name);
		if (value == null) {
			return null;
		} else if (value instanceof CharSequence) {
			return value.toString();
		} else if (value instanceof String []) {
			String[] array = (String[])value;
			if (array.length > 0) {
				return array[0];
			} else {
				return null;
			}
		}
//		return null;
		return value.toString();
	}

	public String getParameter(String name, String defaultValue) {
		return StringUtil.nvl(getParameter(name), defaultValue);
	}

	public String[] getParameterValues(String name) {
		Object value = get(name);
		if (value == null) {
			return null;
		} else if (value instanceof CharSequence) {
			return new String [] { value.toString() };
		} else if (value instanceof String []) {
			return (String []) value;
		}
		return null;
	}
	
	
	public void setSession(HttpSession session) {
    	this.session = session;
    }

    public HttpSession getSession() {
    	return session;
    }

    public void setString(String name, Object defaultValue) {
    	Object value = get(name);
		if (value == null || StringUtil.isEmpty(StringUtil.toString(value))) {
			put(name, defaultValue);
		}
    }

    public String getOnlyNum(String name) {
    	String value = getString(name);
    	if(StringUtil.isNotEmpty(value)) {
    		value = value.replaceAll("[^0-9]", "");
    	}
    	return value;
    }

	public String nvl(String key, String defaultValue) {
		return getParameter(key, defaultValue);
	}

	public String nvl(String key) {
		return nvl(key, "");
	}

}