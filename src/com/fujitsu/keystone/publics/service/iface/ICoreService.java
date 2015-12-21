/**
 *
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Barrie
 */
public interface ICoreService {
    void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    JSONObject getAccessToken(String appid, String appsecret) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getJsapiTicket(String accessToken) throws ConnectionFailedException, WeChatException, AccessTokenException;
}
