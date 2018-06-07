package com.fss.fsswms.base.util.crypto;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class AESUtil {

	private static Key createSecretKey(AESConfig aesConfig) {
		return new SecretKeySpec(aesConfig.getKey().getBytes(), aesConfig.getAlgorithm());
	}
	
	public static byte[] encrypt(byte[] src, AESConfig aesConfig) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(aesConfig.getMode());
		cipher.init(Cipher.ENCRYPT_MODE, createSecretKey(aesConfig));
		return cipher.doFinal(src);
	}
	
	public static String encryptString(byte[] src, AESConfig aesConfig) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return new String(encrypt(src, aesConfig));
	}
	
	public static byte[] encrypt(byte[] src, String mode, String key, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		AESConfig aesConfig = new AESConfig(mode, key, algorithm);
		return encrypt(src, aesConfig);
	}
	
	public static String encryptString(byte[] src, String mode, String key, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return new String(encrypt(src, mode, key, algorithm));
	}

	public static byte[] decrypt(byte[] src, AESConfig aesConfig) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(aesConfig.getMode());
		cipher.init(Cipher.DECRYPT_MODE, createSecretKey(aesConfig));
		return cipher.doFinal(src);
	}
	
	public static String decryptString(byte[] src, AESConfig aesConfig) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return new String(decrypt(src, aesConfig));
	}
	
	public static byte[] decrypt(byte[] src, String mode, String key, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		AESConfig aesConfig = new AESConfig(mode, key, algorithm);
		return decrypt(src, aesConfig);
	}
	
	public static String decryptString(byte[] src, String mode, String key, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return new String(decrypt(src, mode, key, algorithm));
	}
	
	public static void main(String[] args) throws Exception {
		String enKey = Base64Util.encodeString("lkppartners#2017");
//		System.out.println("aeskey encode :: " + enKey);
		
		String deKey = Base64Util.decodeString("bGtwcGFydG5lcnMjMjAxNw==");
//		System.out.println("aeskey decode :: " + deKey);
		
		String data = "";
//		data = "jdbc:oracle:thin:@180.210.34.37:1521:xe";
//		data = "jdbc:mysql://180.210.34.37:3306/fsswms?useUnicode=true&characterEncoding=utf8mb4&autoReconnect=true&allowMultiQueries=true";

//		data = "jdbc:mysql://13.124.196.157:3306/fsswms?useUnicode=true&characterEncoding=utf8mb4&autoReconnect=true&allowMultiQueries=true";
//		data = "FssWmsDev#0526";
		data = "root";
		byte[] enData = AESUtil.encrypt(data.getBytes(), "AES/ECB/PKCS5Padding", "bGtwcGFydG5lcnMjMjAxNw==", "AES");
		String enStr = Base64Util.encodeString(enData);
		System.out.println("암호화 : " + enStr);
		
		enStr = "w2lORhzFkFsFPRGyIx2iju/ysImUctXTL4sqBiGH/7OADpuZdyaDOONsHLT7AYzjyr4YsMCfxNk1jjODJbYIO5TIr8eqznRyUbGyXKKM+4CuFYCoqFfNJEuNImTWstHBkuFPlaMyj1wNO+QHOQCFYw==";
		byte[] deData = AESUtil.decrypt(Base64Util.decode(enStr), "AES/ECB/PKCS5Padding", "bGtwcGFydG5lcnMjMjAxNw==", "AES");
		System.out.println("복호화 : " + new String(deData));
	}
	
}
