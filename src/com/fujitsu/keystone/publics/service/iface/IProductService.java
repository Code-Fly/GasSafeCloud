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
	JSONObject getProductList(String accessToken, int status) throws ConnectionFailedException;

	JSONObject getProduct(String accessToken, String productId) throws ConnectionFailedException;

	JSONObject getProductGroupList(String accessToken) throws ConnectionFailedException;

	JSONObject getProductGroupDetail(String accessToken, String groupId) throws ConnectionFailedException;
}
