/**
 * 
 */
package com.manicure.keystone.entity.customer.message;

/**
 * @author Barrie
 *
 */
public class CouponMessage extends BaseMessage {
	private WxCard wxcard;

	/**
	 * @return the wxcard
	 */
	public WxCard getWxcard() {
		return wxcard;
	}

	/**
	 * @param wxcard
	 *            the wxcard to set
	 */
	public void setWxcard(WxCard wxcard) {
		this.wxcard = wxcard;
	}

}
