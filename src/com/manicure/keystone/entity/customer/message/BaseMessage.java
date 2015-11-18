/**
 * 
 */
package com.manicure.keystone.entity.customer.message;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class BaseMessage extends BaseEntity {
	private String touser;
	private String msgtype;

	/**
	 * @return the touser
	 */
	public String getTouser() {
		return touser;
	}

	/**
	 * @param touser
	 *            the touser to set
	 */
	public void setTouser(String touser) {
		this.touser = touser;
	}

	/**
	 * @return the msgtype
	 */
	public String getMsgtype() {
		return msgtype;
	}

	/**
	 * @param msgtype
	 *            the msgtype to set
	 */
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

}
