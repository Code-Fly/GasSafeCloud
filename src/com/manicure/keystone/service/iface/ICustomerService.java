/**
 * 
 */
package com.manicure.keystone.service.iface;

import net.sf.json.JSONObject;

import com.manicure.keystone.entity.customer.message.TextMessage;

/**
 * @author Barrie
 *
 */
public interface ICustomerService {
	public JSONObject sendTextMessage(String accessToken, TextMessage message);
}
