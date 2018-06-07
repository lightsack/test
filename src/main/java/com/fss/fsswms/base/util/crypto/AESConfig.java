package com.fss.fsswms.base.util.crypto;

public class AESConfig {

	private String mode = "AES/ECB/PKCS5Padding";
	private String key;
	private String algorithm = "AES";
	
	public AESConfig() {
	}
	
	public AESConfig(String mode, String key, String algorithm) {
		this.mode = mode;
		this.key = key;
		this.algorithm = algorithm;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getKey() {
		return Base64Util.decodeString(key);
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
