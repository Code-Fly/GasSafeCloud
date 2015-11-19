/**
 * 
 */
package com.fujitsu.keystone.publics.service.iface;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fujitsu.base.exception.ConnectionFailedException;

import net.sf.json.JSONObject;

/**
 * @author Barrie
 *
 */
public interface ICoreService {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	public JSONObject getAccessToken(String appid, String appsecret) throws ConnectionFailedException;

	public JSONObject getJsapiTicket(String accessToken) throws ConnectionFailedException;
}