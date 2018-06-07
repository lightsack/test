package com.fss.fsswms.base.util.crypto;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
	
	public static byte[] encode(byte[] bytes) {
		return Base64.encodeBase64(bytes); 
	}

	public static byte[] encode(String str) {
		return encode(str.getBytes());
	}
	
	public static String encodeString(byte[] bytes) {
		return new String(encode(bytes));
	}
	
	public static String encodeString(String str) {
		return encodeString(str.getBytes());
	}
	
	public static byte[] decode(byte[] bytes) {
		return Base64.decodeBase64(bytes);
	}
	
	public static byte[] decode(String str) {
		return decode(str.getBytes());
	}
	
	public static String decodeString(byte[] bytes) {
		return new String(decode(bytes));
	}
	
	public static String decodeString(String str) {
		return decodeString(str.getBytes());
	}
	
}
