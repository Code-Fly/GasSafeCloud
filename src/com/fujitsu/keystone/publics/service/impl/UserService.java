/**
 * 
 */
package com.fujitsu.keystone.publics.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.constants.Const;
import com.fujitsu.base.helper.FileUtil;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.entity.account.WeChatUserInfo;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IUserService;

/**
 * @author Barrie
 *
 */
@Service
public class UserService extends BaseService implements IUserService {
	@Resource
	ICoreService coreService;

	/**
	 * 获取网页授权凭证
	 * 
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 * @throws ConnectionFailedException 
	 */
	public JSONObject getOauth2AccessToken(String appId, String appSecret, String code) throws ConnectionFailedException {
		// WeChatOauth2Token wat = null;

		String url = URL_SNS_OAUTH2_TOKEN_GET.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
		// 获取网页授权凭证
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", null);

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;

	}

	/**
	 * 刷新网页授权凭证
	 * 
	 * @param appId
	 * @param refreshToken
	 * @return
	 * @throws ConnectionFailedException 
	 */
	public JSONObject refreshOauth2AccessToken(String appId, String refreshToken) throws ConnectionFailedException {
		// WeChatOauth2Token wat = null;

		String url = URL_SNS_OAUTH2_TOKEN_REFRESH.replace("APPID", appId).replace("REFRESH_TOKEN", refreshToken);
		// 刷新网页授权凭证
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", null);

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
	}

	/**
	 * 通过网页授权获取用户信息
	 * 
	 * @param accessToken
	 *            网页授权接口调用凭证
	 * @param openId
	 *            用户标识
	 * @return SNSUserInfo
	 * @throws ConnectionFailedException 
	 */
	// @SuppressWarnings({ "deprecation", "unchecked" })
	public JSONObject getSNSUserInfo(String accessToken, String openId) throws ConnectionFailedException {
		// SNSUserInfo snsUserInfo = null;

		String url = URL_USER_GET_SNS_INFO.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 通过网页授权获取用户信息
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", null);

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param openId
	 *            用户标识
	 * @return WeixinUserInfo
	 * @throws ConnectionFailedException 
	 */
	public JSONObject getWeChatUserInfo(String accessToken, String openId) throws ConnectionFailedException {
		// WeChatUserInfo wechatUserInfo = null;

		String url = URL_USER_GET_INFO.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", null);

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
	}

	public JSONObject getWeChatUserInfo(HttpServletRequest request, String accessToken, String openId) throws ConnectionFailedException {

		JSONObject resp = getWeChatUserInfo(accessToken, openId);
		if (resp.containsKey("errcode")) {
			logger.error(resp.toString());
			return resp;
		}
		WeChatUserInfo weUserInfo = (WeChatUserInfo) JSONObject.toBean(resp, WeChatUserInfo.class);
		String headimgurl = Const.getServerUrl(request) + FileUtil.getWeChatImage(weUserInfo.getHeadimgurl() + "?wx_fmt=jpeg", FileUtil.CATEGORY_USER, weUserInfo.getOpenid(), false);
		weUserInfo.setHeadimgurl(headimgurl);
		return JSONObject.fromObject(weUserInfo);
	}

	/**
	 * 获取关注者列表
	 * 
	 * @param accessToken
	 * @param nextOpenId
	 * @return
	 * @throws ConnectionFailedException 
	 */
	public JSONObject getWeChatUserList(String accessToken, String nextOpenId) throws ConnectionFailedException {
		// WeChatUserList wechatUserList = null;
		if (null == nextOpenId)
			nextOpenId = "";

		String url = URL_USER_GET_LIST.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenId);

		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", null);

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
	}

	public JSONObject getWeChatUserGroupList(String accessToken) throws ConnectionFailedException {

		String url = URL_USER_GROUP_GET_LIST.replace("ACCESS_TOKEN", accessToken);

		JSONObject response = HttpClientUtil.doHttpsRequest(url, "GET", null);

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
	}

	public JSONObject getWeChatUserGroupByOpenId(String accessToken, String openId) throws ConnectionFailedException {

		String url = URL_USER_GROUP_GET_BY_OPENID.replace("ACCESS_TOKEN", accessToken);
		JSONObject request = new JSONObject();
		request.put("openid", openId);
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", request.toString());

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
	}

}
