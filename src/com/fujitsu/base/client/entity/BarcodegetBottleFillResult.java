/**
 * 
 */
package com.fujitsu.base.client.entity;

/**
 * "result":[{"bfrq":"","rno":"CCST32A0002",
 * "updateTime":"2015-10-08 13:12:12","eptWeight":0,"checkResult":"",
 * "pCode":"","pid":45612,"workNum":"","pDate":"",
 * "checkDatetimeStart":"","infoLabel":"3A4DE632","infoLabeltype":0,
 * "syzbh":"","fillMode":"0","bf":0,"fillWeight":0,"zcdm":"",
 * "checkDatetimeEnd":"","checkdatetime":"2015-10-08 13:16:33"}
 * @author Administrator
 * 最近两笔信息
 */
public class BarcodegetBottleFillResult {
	String bfrq;
	String rno;
	String updateTime;
	int eptWeight;
	String checkResult;
	String pCode;
	int pid;
	String workNum;
	String pDate;
	String checkDatetimeStart;
	String infoLabel;
	int infoLabeltype;
	String syzbh;
	String fillMode;
	int bf;
	int fillWeight;
	String zcdm;
	String checkDatetimeEnd;
	String checkdatetime;
	public String getBfrq() {
		return bfrq;
	}
	public void setBfrq(String bfrq) {
		this.bfrq = bfrq;
	}
	public String getRno() {
		return rno;
	}
	public void setRno(String rno) {
		this.rno = rno;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getEptWeight() {
		return eptWeight;
	}
	public void setEptWeight(int eptWeight) {
		this.eptWeight = eptWeight;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getWorkNum() {
		return workNum;
	}
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	public String getpDate() {
		return pDate;
	}
	public void setpDate(String pDate) {
		this.pDate = pDate;
	}
	public String getCheckDatetimeStart() {
		return checkDatetimeStart;
	}
	public void setCheckDatetimeStart(String checkDatetimeStart) {
		this.checkDatetimeStart = checkDatetimeStart;
	}
	public String getInfoLabel() {
		return infoLabel;
	}
	public void setInfoLabel(String infoLabel) {
		this.infoLabel = infoLabel;
	}
	public int getInfoLabeltype() {
		return infoLabeltype;
	}
	public void setInfoLabeltype(int infoLabeltype) {
		this.infoLabeltype = infoLabeltype;
	}
	public String getSyzbh() {
		return syzbh;
	}
	public void setSyzbh(String syzbh) {
		this.syzbh = syzbh;
	}
	public String getFillMode() {
		return fillMode;
	}
	public void setFillMode(String fillMode) {
		this.fillMode = fillMode;
	}
	public int getBf() {
		return bf;
	}
	public void setBf(int bf) {
		this.bf = bf;
	}
	public int getFillWeight() {
		return fillWeight;
	}
	public void setFillWeight(int fillWeight) {
		this.fillWeight = fillWeight;
	}
	public String getZcdm() {
		return zcdm;
	}
	public void setZcdm(String zcdm) {
		this.zcdm = zcdm;
	}
	public String getCheckDatetimeEnd() {
		return checkDatetimeEnd;
	}
	public void setCheckDatetimeEnd(String checkDatetimeEnd) {
		this.checkDatetimeEnd = checkDatetimeEnd;
	}
	public String getCheckdatetime() {
		return checkdatetime;
	}
	public void setCheckdatetime(String checkdatetime) {
		this.checkdatetime = checkdatetime;
	}
	
}
