package com.fss.fsswms.base.persistence;

public interface CmnConstants {

	public static final String CHARSET = "UTF-8";
	
	/* Session Key */
	public static final String SESSION_USER = "SESSION_USER";
	public static final String SESSION_USER_ID = "SESSION_USER_ID";
	public static final String SESSION_USER_NM = "SESSION_USER_NM";
	public static final String SESSION_USER_PROG_AUTH_LIST = "SESSION_USER_PROG_AUTH_LIST";
	public static final String SESSION_USER_CODE_LIST = "SESSION_USER_CODE_LIST";
	public static final String SESSION_WH_CLS_LIST = "SESSION_WH_CLS_LIST";
	
	public static final String SESSION_CUST = "SESSION_CUST";
	public static final String SESSION_PSPT_NO = "SESSION_PSPT_NO";
	

	/* Request Key */
	public static final String REQUEST_PARAM = "paramBox";
	public static final String REQUEST_PARAM_ORG = "REQUEST_PARAM_ORG";
	public static final String REQUEST_MENU = "REQUEST_MENU";
	public static final String REQUEST_MENU_ID = "REQUEST_MENU_ID";
	public static final String REQUEST_TOP_MENU_ID = "REQUEST_TOP_MENU_ID";


	/* Response Key */
	public static final String RES_CODE = "responseCode";
	public static final String RES_MSG = "responseMessage";
	
	
	/* Excel */
	public static final String EXCEL_NAME = "EXCEL_NAME";
	public static final String EXCEL_COLUMN = "EXCEL_COLUMN";
	public static final String EXCEL_LIST = "EXCEL_LIST";
	public static final String EXCEL_SEARCH = "EXCEL_SEARCH";
	public static final String EXCEL_ETC = "EXCEL_ETC";
	
	/* Login Error */
	public static final String LOGIN_NO_ID = "LOGIN_NO_ID";								//ID가 없습니다
	public static final String LOGIN_PASSWORD_NOT_EQUAL = "LOGIN_PASSWORD_NOT_EQUAL";	//비밀번호가 맞지 않습니다
	
}