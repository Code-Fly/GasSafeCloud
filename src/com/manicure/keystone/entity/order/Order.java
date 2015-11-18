/**
 * 
 */
package com.manicure.keystone.entity.order;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class Order extends BaseEntity {
	private String errcode;
	private String errmsg;
	private OrderBase order;

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
	 * @return the order
	 */
	public OrderBase getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(OrderBase order) {
		this.order = order;
	}

}
