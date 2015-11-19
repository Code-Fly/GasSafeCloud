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
public interface IUserService {
	public JSONObject getOauth2AccessToken(String appId, String appSecret, String code) throws ConnectionFailedException;

	public JSONObject refreshOauth2AccessToken(String appId, String refreshToken) throws ConnectionFailedException;

	public JSONObject getSNSUserInfo(String accessToken, String openId) throws ConnectionFailedException;

	public JSONObject getWeChatUserInfo(String accessToken, String openId) throws ConnectionFailedException;

	public JSONObject getWeChatUserInfo(HttpServletRequest request, String accessToken, String openId) throws ConnectionFailedException;

	public JSONObject getWeChatUserList(String accessToken, String nextOpenId) throws ConnectionFailedException;

	public JSONObject getWeChatUserGroupList(String accessToken) throws ConnectionFailedException;

	public JSONObject getWeChatUserGroupByOpenId(String accessToken, String openId) throws ConnectionFailedException;
}
