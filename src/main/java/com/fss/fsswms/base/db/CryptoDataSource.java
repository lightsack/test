package com.fss.fsswms.base.db;

import org.apache.commons.dbcp.BasicDataSource;

import com.fss.fsswms.base.util.crypto.AESConfig;
import com.fss.fsswms.base.util.crypto.AESUtil;
import com.fss.fsswms.base.util.crypto.Base64Util;

public class CryptoDataSource extends BasicDataSource {

	private AESConfig aesConfig;
	
	public CryptoDataSource() {
	}
	
	public CryptoDataSource(AESConfig aesConfig) {
		this.aesConfig = aesConfig;
	}
	
	public void setAesConfig(AESConfig aesConfig) {
		this.aesConfig = aesConfig;
	}
	
	@Override
	public void setUrl(String url) {
		try {
			super.setUrl(AESUtil.decryptString(Base64Util.decode(url), aesConfig));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setUsername(String username) {
		try {
			super.setUsername(AESUtil.decryptString(Base64Util.decode(username), aesConfig));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setPassword(String password) {
		try {
			super.setPassword(AESUtil.decryptString(Base64Util.decode(password), aesConfig));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}