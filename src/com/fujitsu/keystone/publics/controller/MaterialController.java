/**
 * 
 */
package com.fujitsu.keystone.publics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IMaterialService;

/**
 * @author Barrie
 *
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class MaterialController extends BaseController {
	@Resource
	IMaterialService materialService;
	@Resource
	ICoreService coreService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ConnectionFailedException
	 * @throws AccessTokenException 
	 */
	@RequestMapping(value = "/material/list/{type}/{offset}/{count}", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getMaterialList(HttpServletRequest request, HttpServletResponse response, @PathVariable String type, @PathVariable int offset, @PathVariable int count)
			throws ConnectionFailedException, AccessTokenException, WeChatException {
		// 调用接口获取access_token
		String at = KeystoneUtil.getAccessToken();

		JSONObject resp = materialService.getMaterialList(at, type, offset, count);

		return resp.toString();
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param mediaId
	 * @return
	 * @throws ConnectionFailedException
	 * @throws AccessTokenException 
	 */
	@RequestMapping(value = "/material/query/{mediaId}", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getMaterial(HttpServletRequest request, HttpServletResponse response, @PathVariable String mediaId) throws ConnectionFailedException, AccessTokenException, WeChatException {
		// 调用接口获取access_token
		String at = KeystoneUtil.getAccessToken();

		JSONObject resp = materialService.getMaterial(at, mediaId);

		return resp.toString();

	}
}
