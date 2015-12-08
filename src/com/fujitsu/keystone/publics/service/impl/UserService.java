/**
 * 
 */
package com.fujitsu.keystone.publics.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.WeChatClientUtil;
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
	public JSONObject getOauth2AccessToken(String appId, String appSecret, String code) throws ConnectionFailedException, WeChatException {
		// WeChatOauth2Token wat = null;

		String url = Const.PublicPlatform.URL_SNS_OAUTH2_TOKEN_GET.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
		// 获取网页授权凭证
		String response = WeChatClientUtil.post(url, "UTF-8");

		return JSONObject.fromObject(response);

	}

	/**
	 * 刷新网页授权凭证
	 * 
	 * @param appId
	 * @param refreshToken
	 * @return
	 * @throws ConnectionFailedException 
	 */
	public JSONObject refreshOauth2AccessToken(String appId, String refreshToken) throws ConnectionFailedException, WeChatException {
		// WeChatOauth2Token wat = null;

		String url = Const.PublicPlatform.URL_SNS_OAUTH2_TOKEN_REFRESH.replace("APPID", appId).replace("REFRESH_TOKEN", refreshToken);
		// 刷新网页授权凭证
		String response = WeChatClientUtil.post(url, "UTF-8");

		return JSONObject.fromObject(response);
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
	public JSONObject getSNSUserInfo(String accessToken, String openId) throws ConnectionFailedException, WeChatException {
		// SNSUserInfo snsUserInfo = null;

		String url = Const.PublicPlatform.URL_USER_GET_SNS_INFO.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 通过网页授权获取用户信息
		String response = WeChatClientUtil.post(url, "UTF-8");

		return JSONObject.fromObject(response);
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
	public JSONObject getWeChatUserInfo(String accessToken, String openId) throws ConnectionFailedException, WeChatException {
		// WeChatUserInfo wechatUserInfo = null;

		String url = Const.PublicPlatform.URL_USER_GET_INFO.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		String response = WeChatClientUtil.post(url, "UTF-8");

		return JSONObject.fromObject(response);
	}

	public JSONObject getWeChatUserInfo(HttpServletRequest request, String accessToken, String openId) throws ConnectionFailedException, WeChatException {

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
	public JSONObject getWeChatUserList(String accessToken, String nextOpenId) throws ConnectionFailedException, WeChatException {
		// WeChatUserList wechatUserList = null;
		if (null == nextOpenId)
			nextOpenId = "";

		String url = Const.PublicPlatform.URL_USER_GET_LIST.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenId);

		String response = WeChatClientUtil.post(url, "UTF-8");

		return JSONObject.fromObject(response);
	}

	public JSONObject getWeChatUserGroupList(String accessToken) throws ConnectionFailedException, WeChatException {

		String url = Const.PublicPlatform.URL_USER_GROUP_GET_LIST.replace("ACCESS_TOKEN", accessToken);

		String response = WeChatClientUtil.get(url, "UTF-8");

		return JSONObject.fromObject(response);
	}

	public JSONObject getWeChatUserGroupByOpenId(String accessToken, String openId) throws ConnectionFailedException, WeChatException {

		String url = Const.PublicPlatform.URL_USER_GROUP_GET_BY_OPENID.replace("ACCESS_TOKEN", accessToken);
		JSONObject request = new JSONObject();
		request.put("openid", openId);

		String response = WeChatClientUtil.post(url, request.toString(), "UTF-8");

		return JSONObject.fromObject(response);
	}

}
