package com.fujitsu.client.entity;

import com.fujitsu.base.helper.CommonUtil;

public class BarcodeUPBottleInfoResult {
	private String opdateTime;
	private String syzbh;
	private String pid;
	private String pcode;
	private String zcdm;
	private String pdate;
	private String openId;
	public String getOpdateTime() {
		return opdateTime;
	}
	public void setOpdateTime(String opdateTime) {
		this.opdateTime = CommonUtil.dateToYearMonthDay(opdateTime);
	}
	public String getSyzbh() {
		return syzbh;
	}
	public void setSyzbh(String syzbh) {
		this.syzbh = syzbh;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getZcdm() {
		return zcdm;
	}
	public void setZcdm(String zcdm) {
		this.zcdm = zcdm;
	}
	public String getPdate() {
		return pdate;
	}
	public void setPdate(String pdate) {
		this.pdate =  CommonUtil.dateToYearMonthDay(pdate);;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
}
