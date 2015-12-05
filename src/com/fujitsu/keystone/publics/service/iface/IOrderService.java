/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import javax.servlet.http.HttpServletRequest;

import com.fujitsu.base.exception.ConnectionFailedException;

import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

/**
 * @author Barrie
 *
 */
public interface IOrderService {
	JSONObject getOrderList(String accessToken, String status, String beginTime, String endTime) throws ConnectionFailedException, WeChatException;

	JSONObject getOrderList(HttpServletRequest request, String accessToken, String status, String beginTime, String endTime) throws ConnectionFailedException, WeChatException;

	JSONObject getOrder(String accessToken, String orderId) throws ConnectionFailedException, WeChatException;

	JSONObject getOrder(HttpServletRequest request, String accessToken, String orderId) throws ConnectionFailedException, WeChatException;

	int getOrderCount(JSONObject oList, String productId);
}
