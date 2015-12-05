/**
 *
 */
package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.service.impl.CoreService;
import com.fujitsu.keystone.publics.service.impl.ShopService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Barrie
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class ShopController extends BaseController {
    @Resource
    CoreService coreService;
    @Resource
    ShopService shopService;

    @RequestMapping(value = "/shop/query/{poiId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getShop(HttpServletRequest request, HttpServletResponse response, @PathVariable String poiId) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = shopService.getShop(request, at, poiId);
        if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
            logger.error(resp.toString());
            return resp.toString();
        }
        return resp.toString();

    }

    @RequestMapping(value = "/shop/list", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getShopList(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "begain", required = false, defaultValue = "0") String begin,
                              @RequestParam(value = "limit", required = false, defaultValue = "50") String limit
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = shopService.getShopList(request, at, begin, limit);
        if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
            logger.error(resp.toString());
            return resp.toString();
        }
        return resp.toString();
    }
}
