/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import net.sf.json.JSONObject;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.keystone.publics.entity.customer.message.CouponMessage;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;

/**
 * @author Barrie
 *
 */
public interface ICustomerService {
	JSONObject sendTextMessage(String accessToken, TextMessage message) throws ConnectionFailedException;
	
	JSONObject sendCouponMessage(String accessToken, CouponMessage message) throws ConnectionFailedException;
}
