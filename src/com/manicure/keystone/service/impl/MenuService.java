/**
 * 
 */
package com.manicure.keystone.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.manicure.base.helper.HttpClientUtil;
import com.manicure.base.service.BaseService;
import com.manicure.keystone.entity.error.ErrorMsg;
import com.manicure.keystone.entity.response.Article;
import com.manicure.keystone.entity.response.NewsMessage;
import com.manicure.keystone.entity.response.TextMessage;
import com.manicure.keystone.service.iface.ICoreService;

/**
 * @author Barrie
 *
 */
@Service
public class MenuService extends BaseService {
	
	public static final String V1001_LOTTERY = "V1001_LOTTERY";
	public static final String V1002_VOUVHER = "V1002_VOUVHER";
	public static final String V1003_NEW_ARRIVED = "V1003_NEW_ARRIVED";
	public static final String V1004_NEWS = "V1004_NEWS";
	public static final String V2001_USER_GUID = "V2001_USER_GUID";
	public static final String V2002_WEB_HOME = "V2002_WEB_HOME";
	public static final String V3001_ADDREDD = "V3001_ADDREDD";
	public static final String V3002_ORDER = "V3002_ORDER";

	@Resource
	ICoreService coreService;
	@Resource
	MessageService messageService;

	/**
	 * 
	 * @param accessToken
	 * @param json
	 * @return
	 */
	public JSONObject create(String accessToken, JSONObject json) {

		// 拼装创建菜单的url
		String url = URL_MENU_CREATE.replace("ACCESS_TOKEN", accessToken);		
		// 调用接口创建菜单
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", json.toString());

		if (null == response) {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrcode("-1");
			errMsg.setErrmsg("server is busy");

			return JSONObject.fromObject(errMsg);
		}
		return response;
	}

	/**
	 * 
	 * @param accessToken
	 * @return
	 */
	public JSONObject get(String accessToken) {
		String url = URL_MENU_GET.replace("ACCESS_TOKEN", accessToken);
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "GET", null);

		if (null == response) {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrcode("-1");
			errMsg.setErrmsg("server is busy");

			return JSONObject.fromObject(errMsg);
		}
		return response;
	}

	/**
	 * 
	 * @param accessToken
	 * @return
	 */
	public JSONObject delete(String accessToken) {
		String url = URL_MENU_DELETE.replace("ACCESS_TOKEN", accessToken);
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "GET", null);

		if (null == response) {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrcode("-1");
			errMsg.setErrmsg("server is busy");

			return JSONObject.fromObject(errMsg);
		}
		return response;
	}

	
}
