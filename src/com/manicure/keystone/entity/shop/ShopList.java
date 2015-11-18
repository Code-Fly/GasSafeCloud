/**
 * 
 */
package com.manicure.keystone.entity.shop;

import java.util.List;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class ShopList extends BaseEntity {
	private String errcode;
	private String errmsg;
	private List<Business> business_list;
	private int total_count;

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
	 * @return the business_list
	 */
	public List<Business> getBusiness_list() {
		return business_list;
	}

	/**
	 * @param business_list
	 *            the business_list to set
	 */
	public void setBusiness_list(List<Business> business_list) {
		this.business_list = business_list;
	}

	/**
	 * @return the total_count
	 */
	public int getTotal_count() {
		return total_count;
	}

	/**
	 * @param total_count
	 *            the total_count to set
	 */
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

}
