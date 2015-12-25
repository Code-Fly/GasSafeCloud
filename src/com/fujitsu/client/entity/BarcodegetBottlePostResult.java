package com.fujitsu.client.entity;

import com.fujitsu.base.helper.CommonUtil;

public class BarcodegetBottlePostResult {
	private String  unitName;
	private String pCode;
	private String userName;
	private String pid;
	private String userinfo;
	private int fillWeight;
	private String psStart;
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}
	public int getFillWeight() {
		return fillWeight;
	}
	public void setFillWeight(int fillWeight) {
		this.fillWeight = fillWeight;
	}
	public String getPsStart() {
		return psStart;
	}
	public void setPsStart(String psStart) {
		this.psStart = CommonUtil.dateToYearMonthDay(psStart);
	}
	
}
