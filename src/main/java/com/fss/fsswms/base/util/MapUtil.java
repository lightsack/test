package com.fss.fsswms.base.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

	private MapUtil() {
	}
	
	public static String getString(Map<?, ?> map, Object key) {
		return StringUtil.toString(map.get(key));
	}
	
	public static int getInt(Map<?, ?> map, Object key) {
		return NumberUtil.toInt(map.get(key));
	}
	
	public static double getDouble(Map<?, ?> map, Object key) {
		return NumberUtil.toDouble(map.get(key));
	}
	
	public static BigDecimal getBigDecimal(Map<?, ?> map, Object key) {
		return NumberUtil.toBigDecimal(map.get(key));
	}
	
	public static Map<String, Object> toMap(Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            for(int i=0; i<=fields.length-1;i++){
                fields[i].setAccessible(true);
                resultMap.put(fields[i].getName(), fields[i].get(obj));
            }
            return resultMap;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
	
}
