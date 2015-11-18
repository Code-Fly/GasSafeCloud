/**
 * 
 */
package com.manicure.keystone.service.iface;

import net.sf.json.JSONObject;

/**
 * @author Barrie
 *
 */
public interface IMaterialService {
	public JSONObject getMaterialList(String accessToken, String type, int offset, int count);

	public JSONObject getMaterial(String accessToken, String mediaId);
}
