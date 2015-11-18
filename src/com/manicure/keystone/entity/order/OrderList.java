/**
 * 
 */
package com.manicure.keystone.entity.order;

import java.util.List;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class OrderList extends BaseEntity {
	private String errcode;
	private String errmsg;
	private List<OrderBase> order_list;

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
	 * @return the order_list
	 */
	public List<OrderBase> getOrder_list() {
		return order_list;
	}

	/**
	 * @param order_list
	 *            the order_list to set
	 */
	public void setOrder_list(List<OrderBase> order_list) {
		this.order_list = order_list;
	}

}
