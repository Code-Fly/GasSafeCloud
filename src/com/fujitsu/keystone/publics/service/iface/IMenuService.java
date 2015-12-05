/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

import com.fujitsu.base.exception.ConnectionFailedException;

/**
 * @author Barrie
 *
 */
public interface IMenuService {
	JSONObject create(String accessToken, JSONObject json) throws ConnectionFailedException, WeChatException;

	JSONObject get(String accessToken) throws ConnectionFailedException, WeChatException;

	JSONObject delete(String accessToken) throws ConnectionFailedException, WeChatException;
}
