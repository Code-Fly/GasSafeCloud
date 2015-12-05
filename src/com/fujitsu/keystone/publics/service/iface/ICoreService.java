/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fujitsu.base.exception.ConnectionFailedException;

import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

/**
 * @author Barrie
 *
 */
public interface ICoreService {
	void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	JSONObject getAccessToken(String appid, String appsecret) throws ConnectionFailedException, WeChatException;

	JSONObject getJsapiTicket(String accessToken) throws ConnectionFailedException, WeChatException;
}
