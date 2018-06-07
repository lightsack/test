package com.fss.fsswms.mvc.classic.cmn.vo.user;

import java.util.Date;

public class UserVO {

	private String userId;
	private String userNm;
	private String psWd;
	private String barId;
	private String telNo;
	private String hpNo;
	private String roleCd;	
	private String office;
	private String dept;
	private String posit;	
	private String regId;
	private Date regTime;
	private String updId;
	private Date updTime;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getPsWd() {
		return psWd;
	}
	public void setPsWd(String psWd) {
		this.psWd = psWd;
	}
	public String getBarId() {
		return barId;
	}
	public void setBarId(String barId) {
		this.barId = barId;
	}	
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getHpNo() {
		return hpNo;
	}
	public void setHpNo(String hpNo) {
		this.hpNo = hpNo;
	}
	public String getRoleCd() {
		return roleCd;
	}
	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPosit() {
		return posit;
	}
	public void setPosit(String posit) {
		this.posit = posit;
	}	
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public Date getUpdTime() {
		return updTime;
	}
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	
	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", userNm=" + userNm + ", psWd=" + psWd + ", barId=" + barId + ", telNo=" + telNo + ", hpNo=" + hpNo + ", roleCd="+ roleCd + ", office="+ office + ", dept="+ dept + ", posit="+ posit
				+ ", regId=" + regId + ", regTime=" + regTime + ", updId=" + updId + ", updTime=" + updTime + "]";
	}
	
}
