/**
 * 
 */
package com.manicure.keystone.entity.product;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class Product extends BaseEntity {
	private int errcode;
	private String errmsg;
	private ProductInfo product_info;

	/**
	 * @return the errcode
	 */
	public int getErrcode() {
		return errcode;
	}

	/**
	 * @param errcode
	 *            the errcode to set
	 */
	public void setErrcode(int errcode) {
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
	 * @return the product_info
	 */
	public ProductInfo getProduct_info() {
		return product_info;
	}

	/**
	 * @param product_info
	 *            the product_info to set
	 */
	public void setProduct_info(ProductInfo product_info) {
		this.product_info = product_info;
	}

}
