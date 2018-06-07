package com.fss.fsswms.base.mybatis;

import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import com.fss.fsswms.base.util.StringUtil;

public class CamelKeyMapWrapper extends MapWrapper {
	public CamelKeyMapWrapper(MetaObject metaObject, Map<String, Object> map) {
		super(metaObject,map);
	}
	public String findProperty(String name, boolean useCamelCaseMapping) {
		return StringUtil.camelToLower(name);
	}
}
