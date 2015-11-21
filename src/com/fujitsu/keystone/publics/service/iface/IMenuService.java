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
	JSONObject create(String accessToken, JSONObject json) throws ConnectionFailedException ;

	JSONObject get(String accessToken) throws ConnectionFailedException;
	
	JSONObject delete(String accessToken) throws ConnectionFailedException;
}
