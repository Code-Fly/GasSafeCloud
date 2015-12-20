/**
 *
 */
package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IUserService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Barrie
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class UserController extends BaseController {
    @Resource
    ICoreService coreService;
    @Resource
    IUserService userService;


    /**
     * 获取关注用户信息
     *
     * @param request  request
     * @param response response
     * @param openId   openId
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/user/query/{openId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getWeChatUserInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable String openId) throws ConnectionFailedException, AccessTokenException, WeChatException {
        // 调用接口获取access_token
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = userService.getWeChatUserInfo(request, at, openId);

        return resp.toString();

    }

}
