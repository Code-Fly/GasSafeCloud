/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import javax.servlet.http.HttpServletRequest;

import com.fujitsu.base.exception.ConnectionFailedException;

import net.sf.json.JSONObject;

/**
 * @author Barrie
 *
 */
public interface IOrderService {
	public JSONObject getOrderList(String accessToken, String status, String beginTime, String endTime) throws ConnectionFailedException;

	public JSONObject getOrderList(HttpServletRequest request, String accessToken, String status, String beginTime, String endTime) throws ConnectionFailedException;

	public JSONObject getOrder(String accessToken, String orderId) throws ConnectionFailedException;

	public JSONObject getOrder(HttpServletRequest request, String accessToken, String orderId) throws ConnectionFailedException;

	public int getOrderCount(JSONObject oList, String productId);
}