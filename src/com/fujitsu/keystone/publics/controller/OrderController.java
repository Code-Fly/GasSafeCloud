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
import com.fujitsu.keystone.publics.service.impl.OrderService;
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
public class OrderController extends BaseController {
    @Resource
    CoreService coreService;
    @Resource
    OrderService orderService;

    @RequestMapping(value = "/order/list/{status}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getOrderList(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable String status,
                               @RequestParam(value = "beginTime", required = false, defaultValue = "0") String beginTime,
                               @RequestParam(value = "endTime", required = false, defaultValue = "0") String endTime
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = orderService.getOrderList(request, at, status, beginTime, endTime);

        return resp.toString();
    }

    @RequestMapping(value = "/order/query/{orderId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getOrder(HttpServletRequest request, HttpServletResponse response, @PathVariable String orderId) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = orderService.getOrder(request, at, orderId);

        return resp.toString();
    }
}
