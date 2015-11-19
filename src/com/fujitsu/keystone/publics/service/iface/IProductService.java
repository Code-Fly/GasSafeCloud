/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import net.sf.json.JSONObject;

/**
 * @author Barrie
 *
 */
public interface IProductService {
	public JSONObject getProductList(String accessToken, int status);

	public JSONObject getProduct(String accessToken, String productId);

	public JSONObject getProductGroupList(String accessToken);

	public JSONObject getProductGroupDetail(String accessToken, String groupId);
}
