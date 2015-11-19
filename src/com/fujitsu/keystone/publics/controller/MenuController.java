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
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IMenuService;

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

	@RequestMapping(value = "/menu/create", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ConnectionFailedException, AccessTokenException {
		String baseUrl = Const.MERCHANT_DOMAIN;
		// 调用接口获取access_token
		String at = KeystoneUtil.getAccessToken();
		
		String menuStr = ConfigUtil.getJson("menu.json");
		
		// 调用接口创建菜单
		JSONObject resp = JSONObject.fromObject(menuService.create(at, JSONObject.fromObject(menuStr)));
		return resp.toString();
	}
}
