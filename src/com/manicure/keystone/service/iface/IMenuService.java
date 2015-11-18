/**
 * 
 */
package com.manicure.keystone.service.iface;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

/**
 * @author Barrie
 *
 */
public interface IMenuService {
	public JSONObject create(JSONObject json, String accessToken);

	public JSONObject get(String accessToken);

	public JSONObject delete(String accessToken);

	public String processRequest(HttpServletRequest request);
}
