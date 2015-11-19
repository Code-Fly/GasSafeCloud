/**
 * 
 */
package com.fujitsu.keystone.publics.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.ConfigUtil;
import com.fujitsu.base.helper.Const;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.base.helper.UrlUtil;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IMenuService;
import com.fujitsu.keystone.publics.service.impl.MenuService;

/**
 * @author Barrie
 *
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class MenuController extends BaseController {
	@Resource
	IMenuService menuService;
	@Resource
	ICoreService coreService;

	@RequestMapping(value = "/menu/create")
	@ResponseBody
	public String create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ConnectionFailedException, AccessTokenException {
		String baseUrl = Const.MERCHANT_DOMAIN;
		// 调用接口获取access_token
		String at = KeystoneUtil.getAccessToken();
		
		String menuStr = ConfigUtil.getJson("menu.json");

		String urlHome = URL_SNS_OAUTH2_REDIRECT.replace("REDIRECT_URI", UrlUtil.toUTF8(baseUrl + "/mobile/index")).replace("APPID", APP_ID).replace("SCOPE", "snsapi_base").replace("STATE", "STATE");
		menuStr = menuStr.replace(MenuService.V2002_WEB_HOME, urlHome);
		String urlOrder = URL_SNS_OAUTH2_REDIRECT.replace("REDIRECT_URI", UrlUtil.toUTF8(baseUrl + "/mobile/order-list")).replace("APPID", APP_ID).replace("SCOPE", "snsapi_base")
				.replace("STATE", "STATE");
		menuStr = menuStr.replace(MenuService.V3002_ORDER, urlOrder);
		String urlNewArrived = URL_SNS_OAUTH2_REDIRECT.replace("REDIRECT_URI", UrlUtil.toUTF8(baseUrl + "/mobile/product-list?groupId=208216165&orderBy=sales&sort=desc")).replace("APPID", APP_ID)
				.replace("SCOPE", "snsapi_base").replace("STATE", "STATE");
		menuStr = menuStr.replace(MenuService.V1003_NEW_ARRIVED, urlNewArrived);
		String urlLottery = URL_SNS_OAUTH2_REDIRECT.replace("REDIRECT_URI", UrlUtil.toUTF8(baseUrl + "/mobile/scratch-card")).replace("APPID", APP_ID).replace("SCOPE", "snsapi_base")
				.replace("STATE", "STATE");
		menuStr = menuStr.replace(MenuService.V1001_LOTTERY, urlLottery);
		String urlAddress = URL_SNS_OAUTH2_REDIRECT.replace("REDIRECT_URI", UrlUtil.toUTF8(baseUrl + "/mobile/shop-list")).replace("APPID", APP_ID).replace("SCOPE", "snsapi_base")
				.replace("STATE", "STATE");
		menuStr = menuStr.replace(MenuService.V3001_ADDREDD, urlAddress);
		logger.info(menuStr);
		// 调用接口创建菜单
		JSONObject resp = JSONObject.fromObject(menuService.create(at, JSONObject.fromObject(menuStr)));
		return resp.toString();
	}
}
