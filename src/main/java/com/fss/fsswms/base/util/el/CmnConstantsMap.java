package com.fss.fsswms.base.util.el;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fss.fsswms.base.persistence.CmnConstants;
import com.fss.fsswms.base.util.StringUtil;

@SuppressWarnings("serial")
public class CmnConstantsMap extends HashMap<String, Object> {
	
	private static final Logger log = LoggerFactory.getLogger(CmnConstantsMap.class);
	private static Map<String, Object> reflectedConstants;
	public static final CmnConstantsMap webConstants = new CmnConstantsMap();

	static {
		reflectedConstants = new HashMap<String, Object>();
		Field[] fields = CmnConstants.class.getFields();
		int i=0, s=fields.length;
		try {
			for(; i<s; i++) {
				reflectedConstants.put(StringUtil.camelToLower(fields[i].getName()), fields[i].get(null));
			}
		} catch (IllegalAccessException ex) {
			log.error("Exception accessing field : " + fields[i].getName());
		}
	}

	public Object get(Object key) {
		Object value = reflectedConstants.get(key);
		if (value == null) {
			throw new IllegalArgumentException("[" + key + "] " + "No such constant defined in class Constants");
		}
		return value;
	}

}