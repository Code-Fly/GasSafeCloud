/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.ConnectionFailedException;

import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

/**
 * @author Barrie
 *
 */
public interface IProductService {
	JSONObject getProductList(String accessToken, int status) throws ConnectionFailedException, WeChatException;

	JSONObject getProduct(String accessToken, String productId) throws ConnectionFailedException, WeChatException;

	JSONObject getProductGroupList(String accessToken) throws ConnectionFailedException, WeChatException;

	JSONObject getProductGroupDetail(String accessToken, String groupId) throws ConnectionFailedException, WeChatException;
}
