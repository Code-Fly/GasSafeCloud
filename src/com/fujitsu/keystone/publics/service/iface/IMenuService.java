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
public interface IMenuService {
	public JSONObject create(String accessToken, JSONObject json) throws ConnectionFailedException ;

	public JSONObject get(String accessToken) throws ConnectionFailedException;

	public String processRequest(HttpServletRequest request);
}
