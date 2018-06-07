package com.fss.fsswms.base.util.crypto;

import java.security.Key;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class DESUtil {
	private static final boolean STRING_ENCODE_BASE64 = false;

	private static final String ALGORITHM = "DES"; //DES, DESEDE, TRIPLEDES

	private Key key = null;

	public void setKey(Key key){
		this.key = key;
	}

	public byte[] encrypt(byte[] data)throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(data);
	}
	
	public static byte[] encrypt(String desKey, byte[] data)throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, getKey(ALGORITHM, Base64.decodeBase64(desKey)));
		return cipher.doFinal(data);
	}

	public byte[] decrypt(byte[] data)throws Exception{
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE,key);
		return cipher.doFinal(data);
	}
	
	public static byte[] decrypt(String desKey, byte[] data)throws Exception{
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, getKey(ALGORITHM, Base64.decodeBase64(desKey)));
		return cipher.doFinal(data);
	}

	public void setDesKey(String desKey) throws Exception {
		key = getKey(ALGORITHM, Base64.decodeBase64(desKey));
	}

	public String encrypt(String planString) throws Exception {
		return encodeString( encrypt(planString.getBytes()) );
	}
	
	public static String encrypt(String desKey, String planString) throws Exception {
		return encodeString( encrypt(desKey, planString.getBytes()) );
	}

	public String decrypt(String encryptedString)throws Exception {
		return new String( decrypt(decodeString(encryptedString)) );
	}
	
	public static String decrypt(String desKey, String encryptedString)throws Exception {
		return new String( decrypt(desKey, decodeString(encryptedString)) );
	}

	public static String encodeString(byte [] data) throws Exception {
		return STRING_ENCODE_BASE64 ? Base64.encodeBase64String(data) : Hex.encodeHexString(data);
	}
	
	public static byte[] decodeString(String data) throws Exception {
		return STRING_ENCODE_BASE64 ? Base64.decodeBase64(data) :  Hex.decodeHex(data.toCharArray());
	}

	private static final Key getKey(String alg, byte[] data) throws Exception {
		String upper = alg.toUpperCase();
		if ("DES".equals(upper)) {
			KeySpec keySpec = new DESKeySpec(data);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(alg);
			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
			return secretKey;
		} else if ("DESEDE".equals(upper) || "TRIPLEDES".equals(upper) ) {
			KeySpec keySpec = new DESedeKeySpec(data);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(alg);
			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
			return secretKey;
		} else {
			SecretKeySpec keySpec = new SecretKeySpec(data, alg);
			return keySpec;
		}
	}

}
