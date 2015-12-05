/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.keystone.publics.entity.customer.message.CouponMessage;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;

/**
 * @author Barrie
 *
 */
public interface ICustomerService {
	JSONObject sendTextMessage(String accessToken, TextMessage message) throws ConnectionFailedException, WeChatException;

	JSONObject sendCouponMessage(String accessToken, CouponMessage message) throws ConnectionFailedException, WeChatException;
}
