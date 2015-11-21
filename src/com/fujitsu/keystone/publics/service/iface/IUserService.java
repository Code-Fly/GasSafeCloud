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
	JSONObject getOauth2AccessToken(String appId, String appSecret, String code) throws ConnectionFailedException;

	JSONObject refreshOauth2AccessToken(String appId, String refreshToken) throws ConnectionFailedException;

	JSONObject getSNSUserInfo(String accessToken, String openId) throws ConnectionFailedException;

	JSONObject getWeChatUserInfo(String accessToken, String openId) throws ConnectionFailedException;

	JSONObject getWeChatUserInfo(HttpServletRequest request, String accessToken, String openId) throws ConnectionFailedException;

	JSONObject getWeChatUserList(String accessToken, String nextOpenId) throws ConnectionFailedException;

	JSONObject getWeChatUserGroupList(String accessToken) throws ConnectionFailedException;

	JSONObject getWeChatUserGroupByOpenId(String accessToken, String openId) throws ConnectionFailedException;
}
