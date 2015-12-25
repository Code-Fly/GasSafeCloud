/**
 * 
 */
package com.fujitsu.client.entity;

import com.fujitsu.base.helper.CommonUtil;

/**
 * {"typeName":"YSP118(YSP-50)","checkDatetimeStart":"",
 * "qpczLicbh":"PZZ苏-1218（17)号","pCode":"MS",
 * "rname":"宜兴中燃能源有限公司","pid":1760,"workNum":"",
 * "fillWeight":0,"czTime":""）
 * @author Administrator
 * 最近两笔信息
 */
public class BarcodegetBottleFillResult {
	private String typeName;
	private String checkDatetimeStart;
	private String qpczLicbh;
	private String pCode;
	private String rname;
	private String pid;
	private String workNum;
	private int fillWeight; 
	private String czTime;
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCheckDatetimeStart() {
		return checkDatetimeStart;
	}
	public void setCheckDatetimeStart(String checkDatetimeStart) {
		this.checkDatetimeStart =CommonUtil.dateToYearMonthDay(checkDatetimeStart);
	}
	public String getQpczLicbh() {
		return qpczLicbh;
	}
	public void setQpczLicbh(String qpczLicbh) {
		this.qpczLicbh = qpczLicbh;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getWorkNum() {
		return workNum;
	}
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	public int getFillWeight() {
		return fillWeight;
	}
	public void setFillWeight(int fillWeight) {
		this.fillWeight = fillWeight;
	}
	public String getCzTime() {
		return czTime;
	}
	public void setCzTime(String czTime) {
		this.czTime = czTime;
	}
	
}
