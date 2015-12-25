package com.fujitsu.client.entity;

import com.fujitsu.base.helper.CommonUtil;

public class BarcodegetBottlePsafeResult {
	private String pCode;
	private String zcdm;
	private String syzbh;
	private String qpStructureName;
	private String className;
	
	private String typeName;
	
	private String pid;
	private String mediumName;
	private String pDate;
	private String fDate;
	private String xjrq;
	private String bfrq;
	private String userName;
	private String userInfo;
	private String phone;
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	public String getZcdm() {
		return zcdm;
	}
	public void setZcdm(String zcdm) {
		this.zcdm = zcdm;
	}
	public String getSyzbh() {
		return syzbh;
	}
	public void setSyzbh(String syzbh) {
		this.syzbh = syzbh;
	}
	public String getQpStructureName() {
		return qpStructureName;
	}
	public void setQpStructureName(String qpStructureName) {
		this.qpStructureName = qpStructureName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getMediumName() {
		return mediumName;
	}
	public void setMediumName(String mediumName) {
		this.mediumName = mediumName;
	}
	public String getpDate() {
		return pDate;
	}
	public void setpDate(String pDate) {
		this.pDate = CommonUtil.dateToYearMonth(pDate);
	}
	public String getfDate() {
		return fDate;
	}
	public void setfDate(String fDate) {
		this.fDate =  CommonUtil.dateToYearMonth(fDate);
	}
	public String getXjrq() {
		return xjrq;
	}
	public void setXjrq(String xjrq) {
		this.xjrq = CommonUtil.dateToYearMonth(xjrq);
	}
	public String getBfrq() {
		return bfrq;
	}
	public void setBfrq(String bfrq) {
		this.bfrq = CommonUtil.dateToYearMonth(bfrq);;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
