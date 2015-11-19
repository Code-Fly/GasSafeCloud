/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import net.sf.json.JSONObject;

import com.fujitsu.base.exception.ConnectionFailedException;

/**
 * @author Barrie
 *
 */
public interface IMenuService {
	public JSONObject create(String accessToken, JSONObject json) throws ConnectionFailedException ;

	public JSONObject get(String accessToken) throws ConnectionFailedException;
	
	public JSONObject delete(String accessToken) throws ConnectionFailedException;
}
