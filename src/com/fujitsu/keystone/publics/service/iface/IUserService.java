/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Barrie
 *
 */
public interface IUserService {

	JSONObject getWeChatUserInfo(String accessToken, String openId) throws ConnectionFailedException, WeChatException;

	JSONObject getWeChatUserInfo(HttpServletRequest request, String accessToken, String openId) throws ConnectionFailedException, WeChatException;

}
