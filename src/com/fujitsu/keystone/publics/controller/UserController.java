/**
 * 
 */
package com.fujitsu.keystone.publics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.entity.ErrorMsg;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.service.impl.CoreService;
import com.fujitsu.keystone.publics.service.impl.UserService;

/**
 * @author Barrie
 *
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class UserController extends BaseController {
	@Resource
	CoreService coreService;
	@Resource
	UserService userService;

	/**
	 * 获取SNS User信息
	 * 
	 * @param request
	 * @param response
	 * @param openId
	 * @param accessToken
	 * @return
	 */
	@RequestMapping(value = "/user/sns/query/{openId}/{accessToken}")
	@ResponseBody
	public String getSNSUserInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable String openId, @PathVariable String accessToken) {

		JSONObject resp = userService.getSNSUserInfo(accessToken, openId);
		if (resp.containsKey("errcode")) {
			logger.error(resp.toString());
			return resp.toString();
		}
		return resp.toString();
	}

	/**
	 * SNS User OAuth登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ConnectionFailedException 
	 */
	@RequestMapping(value = "/user/sns/oauth")
	@ResponseBody
	public String SNSUserOAuth(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException {

		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		// 用户同意授权
		if (!"authdeny".equals(code) && null != code) {
			// 获取网页授权access_token
			JSONObject sat = userService.getOauth2AccessToken(APP_ID, APP_SECRET, code);
			if (sat.containsKey("errcode")) {
				logger.error(sat.toString());
				return sat.toString();
			}
			// 用户标识
			String openId = sat.getString("openid");
			if ("snsapi_userinfo".equals(sat.getString("scope"))) {

				JSONObject resp = userService.getSNSUserInfo(sat.getString("access_token"), openId);
				if (resp.containsKey("errcode")) {
					logger.error(resp.toString());
					return resp.toString();
				}
				logger.info(resp.toString());
				return resp.toString();
			} else {
				// 调用接口获取access_token
				JSONObject at = coreService.getAccessToken(APP_ID, APP_SECRET);
				if (at.containsKey("errcode")) {
					logger.error(at.toString());
					return at.toString();
				}

				JSONObject resp = userService.getWeChatUserInfo(request, at.getString("access_token"), openId);
				if (resp.containsKey("errcode")) {
					logger.error(resp.toString());
					return resp.toString();
				}
				logger.info(resp.toString());
				return resp.toString();
			}

		}
		ErrorMsg errMsg = new ErrorMsg();
		errMsg.setErrcode("-1");
		errMsg.setErrmsg("not authorised");
		return JSONObject.fromObject(errMsg).toString();
	}

	/**
	 * 获取微信User信息
	 * 
	 * @param request
	 * @param response
	 * @param openId
	 * @return
	 */
	@RequestMapping(value = "/user/query/{openId}")
	@ResponseBody
	public String getWeChatUserInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable String openId) {
		// 调用接口获取access_token
		String at = KeystoneUtil.getAccessToken();
		if (null == at) {
			logger.error(KeystoneUtil.getErrmsg());
			return KeystoneUtil.getErrmsg();
		}

		JSONObject resp = userService.getWeChatUserInfo(request, at, openId);
		if (resp.containsKey("errcode")) {
			logger.error(resp.toString());
			return resp.toString();
		}
		return resp.toString();

	}

	@RequestMapping(value = "/user/list/{nextOpenId}")
	@ResponseBody
	public String getWeChatUserList(HttpServletRequest request, HttpServletResponse response, @PathVariable String nextOpenId) {
		String at = KeystoneUtil.getAccessToken();
		if (null == at) {
			logger.error(KeystoneUtil.getErrmsg());
			return KeystoneUtil.getErrmsg();
		}
		if ("0".equals(nextOpenId))
			nextOpenId = null;

		JSONObject resp = userService.getWeChatUserList(at, nextOpenId);
		if (resp.containsKey("errcode")) {
			logger.error(resp.toString());
			return resp.toString();
		}
		return resp.toString();
	}

	@RequestMapping(value = "/user/group/list")
	@ResponseBody
	public String getWeChatUserGroupList(HttpServletRequest request, HttpServletResponse response) {
		String at = KeystoneUtil.getAccessToken();
		if (null == at) {
			logger.error(KeystoneUtil.getErrmsg());
			return KeystoneUtil.getErrmsg();
		}

		JSONObject resp = userService.getWeChatUserGroupList(at);
		if (resp.containsKey("errcode")) {
			logger.error(resp.toString());
			return resp.toString();
		}
		return resp.toString();
	}

	@RequestMapping(value = "/user/group/query/{openId}")
	@ResponseBody
	public String getWeChatUserGroupByOpenId(HttpServletRequest request, HttpServletResponse response, @PathVariable String openId) {
		String at = KeystoneUtil.getAccessToken();
		if (null == at) {
			logger.error(KeystoneUtil.getErrmsg());
			return KeystoneUtil.getErrmsg();
		}

		JSONObject resp = userService.getWeChatUserGroupByOpenId(at, openId);
		if (resp.containsKey("errcode")) {
			logger.error(resp.toString());
			return resp.toString();
		}
		return resp.toString();
	}
}
