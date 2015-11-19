/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.ConnectionFailedException;

import net.sf.json.JSONObject;

/**
 * @author Barrie
 *
 */
public interface IProductService {
	public JSONObject getProductList(String accessToken, int status) throws ConnectionFailedException;

	public JSONObject getProduct(String accessToken, String productId) throws ConnectionFailedException;

	public JSONObject getProductGroupList(String accessToken) throws ConnectionFailedException;

	public JSONObject getProductGroupDetail(String accessToken, String groupId) throws ConnectionFailedException;
}
