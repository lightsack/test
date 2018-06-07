package com.fss.fsswms.base.util;

import java.text.MessageFormat;

public class MsgUtil {

	public static String getMsg(String msg, Object[] messageParams) {
		if(StringUtil.isNotEmpty(msg)) {
			if(messageParams != null && messageParams.length > 0) {
				msg = MessageFormat.format(msg, messageParams);
			}
		}
		return msg;
	}
}
