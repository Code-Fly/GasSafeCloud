/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import net.sf.json.JSONObject;

import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;

/**
 * @author Barrie
 *
 */
public interface ICustomerService {
	public JSONObject sendTextMessage(String accessToken, TextMessage message);
}
