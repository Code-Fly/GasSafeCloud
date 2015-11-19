/**
 * 
 */
package com.fujitsu.keystone.publics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.FileUtil;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.base.helper.UrlUtil;
import com.fujitsu.keystone.publics.service.impl.CoreService;

/**
 * @author Barrie
 *
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class CoreController extends BaseController {

	@Resource
	CoreService coreService;

	@RequestMapping(value = "/core")
	public void connect(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getMethod();
		logger.info(method);
		if ("GET".equals(method)) {
			try {
				coreService.doGet(request, response);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		} else if ("POST".equals(method)) {
			try {
				coreService.doPost(request, response);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

	}

	@RequestMapping(value = "/token/refresh")
	@ResponseBody
	public String refreshToken(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException {
		return KeystoneUtil.refreshLocalAccessToken().toString();
	}

	@RequestMapping(value = "/token/query")
	@ResponseBody
	public String queryToken(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException {
		return KeystoneUtil.getLocalAccessToken().toString();
	}

	@RequestMapping(value = "/jsapi/ticket/query")
	@ResponseBody
	public String queryJsapiTicket(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, AccessTokenException {
		JSONObject resp = coreService.getJsapiTicket(KeystoneUtil.getAccessToken());
		return resp.getString("ticket");
	}

	@RequestMapping(value = "/file/image/product")
	@ResponseBody
	public String getImage(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getParameter("url");
		String pid = request.getParameter("pid");
		if (null != url || null == pid) {
			url = FileUtil.getWeChatImage(url, FileUtil.CATEGORY_PRODUCT, pid, false);
		}
		return url;

	}

	@RequestMapping(value = "/url/encode")
	@ResponseBody
	public String urlEncoder(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getParameter("url");
		if (null != url) {
			url = UrlUtil.toUTF8(url);
		}
		return url;

	}
}
