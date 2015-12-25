package com.fujitsu.client.entity;

import com.fujitsu.base.helper.CommonUtil;

public class BarcodegetBottleResult {
	int pPress;
	String rno;
	int press;
	String syzbh;
	int jyzq;
	String xjrq;
	int bf=30;
	String className;
	int ply;
	String ownName;
	String zybh;
	String bfrq;
	String regTime;
	int status;
	String pid;
	String pnoName;
	String fDate;
	
	String qpStructureName;
	String pDate;
	String typeName;
	int dimension;
	int quality;
	String rnoidTzsbName;
	String zcdm;
	String mediumName;
	String jianname;

	public String getJianname() {
		return jianname;
	}

	public void setJianname(String jianname) {
		this.jianname = jianname;
	}

	public int getpPress() {
		return pPress;
	}
	public void setpPress(int pPress) {
		this.pPress = pPress;
	}
	public String getRno() {
		return rno;
	}
	public void setRno(String rno) {
		this.rno = rno;
	}
	public int getPress() {
		return press;
	}
	public void setPress(int press) {
		this.press = press;
	}
	public String getSyzbh() {
		return syzbh;
	}
	public void setSyzbh(String syzbh) {
		this.syzbh = syzbh;
	}
	public int getJyzq() {
		return jyzq;
	}
	public void setJyzq(int jyzq) {
		this.jyzq = jyzq;
	}
	public String getXjrq() {
		return xjrq;
	}
	public void setXjrq(String xjrq) {
		this.xjrq = CommonUtil.dateToYearMonth(xjrq);
	}
	public int getBf() {
		return bf;
	}
	public void setBf(int bf) {
		this.bf = bf;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getPly() {
		return ply;
	}
	public void setPly(int ply) {
		this.ply = ply;
	}
	public String getOwnName() {
		return ownName;
	}
	public void setOwnName(String ownName) {
		this.ownName = ownName;
	}
	public String getZybh() {
		return zybh;
	}
	public void setZybh(String zybh) {
		this.zybh = zybh;
	}
	public String getBfrq() {
		return bfrq;
	}
	public void setBfrq(String bfrq) {
		this.bfrq = bfrq;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPnoName() {
		return pnoName;
	}
	public void setPnoName(String pnoName) {
		this.pnoName = pnoName;
	}
	public String getfDate() {
		return fDate;
	}
	public void setfDate(String fDate) {
		this.fDate = CommonUtil.dateToYearMonth(fDate);
	}
	public String getQpStructureName() {
		return qpStructureName;
	}
	public void setQpStructureName(String qpStructureName) {
		this.qpStructureName = qpStructureName;
	}
	public String getpDate() {
		return pDate;
	}
	public void setpDate(String pDate) {
		this.pDate = CommonUtil.dateToYearMonth(pDate);
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public String getRnoidTzsbName() {
		return rnoidTzsbName;
	}
	public void setRnoidTzsbName(String rnoidTzsbName) {
		this.rnoidTzsbName = rnoidTzsbName;
	}
	public String getZcdm() {
		return zcdm;
	}
	public void setZcdm(String zcdm) {
		this.zcdm = zcdm;
	}
	public String getMediumName() {
		return mediumName;
	}
	public void setMediumName(String mediumName) {
		this.mediumName = mediumName;
	}
}
