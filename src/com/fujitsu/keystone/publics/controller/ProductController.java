/**
 * 
 */
package com.fujitsu.keystone.publics.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.service.impl.CoreService;
import com.fujitsu.keystone.publics.service.impl.ProductService;

/**
 * @author Barrie
 *
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class ProductController extends BaseController {
	@Resource
	CoreService coreService;
	@Resource
	ProductService productService;

	@RequestMapping(value = "/product/list/{status}")
	@ResponseBody
	public String getProductList(HttpServletRequest request, HttpServletResponse response, @PathVariable int status, @RequestParam(value = "groupId", required = false) String groupId,
			@RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "minPrice", required = false) String minPrice, @RequestParam(value = "maxPrice", required = false) String maxPrice) {
		Map<String, String> filter = new HashMap<String, String>();
		if (null != minPrice) {
			filter.put("minPrice", minPrice);
		}else{
			filter.put("minPrice", "-");
		}
		if (null != maxPrice) {
			filter.put("maxPrice", maxPrice);
		}else{
			filter.put("maxPrice", "-");
		}
		if (null != groupId) {
			filter.put("groupId", groupId);
		} else {
			filter.put("groupId", "0");
		}
		if (null != orderBy) {
			filter.put("orderBy", orderBy);
		} else {
			filter.put("orderBy", "price");
		}
		if (null != sort) {
			filter.put("sort", sort);
		} else {
			filter.put("sort", "asc");
		}
		String at = KeystoneUtil.getAccessToken();
		if (null == at) {
			logger.error(KeystoneUtil.getErrmsg());
			return KeystoneUtil.getErrmsg();
		}

		JSONObject resp = productService.getProductList(request, at, status, filter);
		if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
			logger.error(resp.toString());
			return resp.toString();
		}
		return JSONObject.fromObject(resp).toString();
	}

	@RequestMapping(value = "/product/query/{productId}")
	@ResponseBody
	public String getProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable String productId) {
		String at = KeystoneUtil.getAccessToken();
		if (null == at) {
			logger.error(KeystoneUtil.getErrmsg());
			return KeystoneUtil.getErrmsg();
		}

		JSONObject resp = productService.getProduct(request, at, productId);
		if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
			logger.error(resp.toString());
			return resp.toString();
		}
		return resp.toString();
	}

	@RequestMapping(value = "/product/group/list")
	@ResponseBody
	public String getProductGroupList(HttpServletRequest request, HttpServletResponse response) {
		String at = KeystoneUtil.getAccessToken();
		if (null == at) {
			logger.error(KeystoneUtil.getErrmsg());
			return KeystoneUtil.getErrmsg();
		}

		JSONObject resp = productService.getProductGroupList(at);
		if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
			logger.error(resp.toString());
			return resp.toString();
		}
		return resp.toString();
	}

	@RequestMapping(value = "/product/group/query/{groupId}")
	@ResponseBody
	public String getProductGroupDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable String groupId) {
		String at = KeystoneUtil.getAccessToken();
		if (null == at) {
			logger.error(KeystoneUtil.getErrmsg());
			return KeystoneUtil.getErrmsg();
		}

		JSONObject resp = productService.getProductGroupDetail(at, groupId);
		if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
			logger.error(resp.toString());
			return resp.toString();
		}
		return resp.toString();
	}
}
