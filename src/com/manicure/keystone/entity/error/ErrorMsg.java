/**
 * 
 */
package com.manicure.keystone.entity.error;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class ErrorMsg extends BaseEntity {
	private String errcode;
	private String errmsg;

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

}
