/**
 * 
 */
package com.manicure.keystone.entity.shop;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class Shop extends BaseEntity {
	private String errcode;
	private String errmsg;
	private Business business;

	/**
	 * @return the errcode
	 */
	public String getErrcode() {
		return errcode;
	}

	/**
	 * @param errcode
	 *            the errcode to set
	 */
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	/**
	 * @return the errmsg
	 */
	public String getErrmsg() {
		return errmsg;
	}

	/**
	 * @param errmsg
	 *            the errmsg to set
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/**
	 * @return the business
	 */
	public Business getBusiness() {
		return business;
	}

	/**
	 * @param business
	 *            the business to set
	 */
	public void setBusiness(Business business) {
		this.business = business;
	}

}
