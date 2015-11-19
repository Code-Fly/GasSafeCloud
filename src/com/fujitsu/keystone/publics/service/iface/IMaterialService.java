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
public interface IMaterialService {
	public JSONObject getMaterialList(String accessToken, String type, int offset, int count) throws ConnectionFailedException;

	public JSONObject getMaterial(String accessToken, String mediaId) throws ConnectionFailedException;
}
