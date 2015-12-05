/**
 *
 */
package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.entity.ErrorMsg;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.service.impl.CoreService;
import com.fujitsu.keystone.publics.service.impl.UserService;
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
    CoreService coreService;
    @Resource
    UserService userService;

    /**
     * 获取SNS User信息
     *
     * @param request
     * @param response
     * @param openId
     * @param accessToken
     * @return
     * @throws ConnectionFailedException
     */
    @RequestMapping(value = "/user/sns/query/{openId}/{accessToken}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getSNSUserInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable String openId, @PathVariable String accessToken) throws ConnectionFailedException, WeChatException {

        JSONObject resp = userService.getSNSUserInfo(accessToken, openId);
        if (resp.containsKey("errcode")) {
            logger.error(resp.toString());
            return resp.toString();
        }
        return resp.toString();
    }

    /**
     * SNS User OAuth登录
     *
     * @param request
     * @param response
     * @return
     * @throws ConnectionFailedException
     */
    @RequestMapping(value = "/user/sns/oauth", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String SNSUserOAuth(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, WeChatException {

        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        // 用户同意授权
        if (!"authdeny".equals(code) && null != code) {
            // 获取网页授权access_token
            JSONObject sat = userService.getOauth2AccessToken(Const.WECHART_APP_ID, Const.WECHART_APP_SECRET, code);
            if (sat.containsKey("errcode")) {
                logger.error(sat.toString());
                return sat.toString();
            }
            // 用户标识
            String openId = sat.getString("openid");
            if ("snsapi_userinfo".equals(sat.getString("scope"))) {

                JSONObject resp = userService.getSNSUserInfo(sat.getString("access_token"), openId);
                if (resp.containsKey("errcode")) {
                    logger.error(resp.toString());
                    return resp.toString();
                }
                logger.info(resp.toString());
                return resp.toString();
            } else {
                // 调用接口获取access_token
                JSONObject at = coreService.getAccessToken(Const.WECHART_APP_ID, Const.WECHART_APP_SECRET);
                if (at.containsKey("errcode")) {
                    logger.error(at.toString());
                    return at.toString();
                }

                JSONObject resp = userService.getWeChatUserInfo(request, at.getString("access_token"), openId);
                if (resp.containsKey("errcode")) {
                    logger.error(resp.toString());
                    return resp.toString();
                }
                logger.info(resp.toString());
                return resp.toString();
            }

        }
        ErrorMsg errMsg = new ErrorMsg();
        errMsg.setErrcode("-1");
        errMsg.setErrmsg("not authorised");
        return JSONObject.fromObject(errMsg).toString();
    }

    /**
     * 获取微信User信息
     *
     * @param request
     * @param response
     * @param openId
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     */
    @RequestMapping(value = "/user/query/{openId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getWeChatUserInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable String openId) throws ConnectionFailedException, AccessTokenException, WeChatException {
        // 调用接口获取access_token
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = userService.getWeChatUserInfo(request, at, openId);
        if (resp.containsKey("errcode")) {
            logger.error(resp.toString());
            return resp.toString();
        }
        return resp.toString();

    }

    @RequestMapping(value = "/user/list/{nextOpenId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getWeChatUserList(HttpServletRequest request, HttpServletResponse response, @PathVariable String nextOpenId) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        if ("0".equals(nextOpenId))
            nextOpenId = null;

        JSONObject resp = userService.getWeChatUserList(at, nextOpenId);
        if (resp.containsKey("errcode")) {
            logger.error(resp.toString());
            return resp.toString();
        }
        return resp.toString();
    }

    @RequestMapping(value = "/user/group/list", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getWeChatUserGroupList(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = userService.getWeChatUserGroupList(at);
        if (resp.containsKey("errcode")) {
            logger.error(resp.toString());
            return resp.toString();
        }
        return resp.toString();
    }

    @RequestMapping(value = "/user/group/query/{openId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getWeChatUserGroupByOpenId(HttpServletRequest request, HttpServletResponse response, @PathVariable String openId) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = userService.getWeChatUserGroupByOpenId(at, openId);
        if (resp.containsKey("errcode")) {
            logger.error(resp.toString());
            return resp.toString();
        }
        return resp.toString();
    }
}
