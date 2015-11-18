/**
 * 
 */
package com.manicure.keystone.service.iface;

import net.sf.json.JSONObject;

/**
 * @author Barrie
 *
 */
public interface IShopService {

	public JSONObject getShop(String accessToken, String poi_id);

	public JSONObject getShopList(String accessToken, String begin, String limit);
}
