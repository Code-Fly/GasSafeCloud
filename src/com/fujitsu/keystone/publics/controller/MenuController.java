/**
 *
 */
package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.ConfigUtil;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IMenuService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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
public class MenuController extends BaseController {
    @Resource
    IMenuService menuService;
    @Resource
    ICoreService coreService;

    /**
     * 创建默认菜单
     *
     * @param request  request
     * @param response response
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/menu/create/default", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public String createDetault(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "data", required = false, defaultValue = "0") String data
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {

        if ("0".equals(data)) {
            data = ConfigUtil.getJson("menu-default.json");
        }

        JSONObject resp = JSONObject.fromObject(menuService.createDefault(JSONObject.fromObject(data)));
        return resp.toString();
    }

    /**
     * 创建条件菜单
     *
     * @param request
     * @param response
     * @param data
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/menu/create/condition", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public String createCondition(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "data", required = false, defaultValue = "0") String data
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {

        if ("0".equals(data)) {
            data = ConfigUtil.getJson("menu-condition.json");
        }

        JSONObject resp = JSONObject.fromObject(menuService.createCondition(JSONObject.fromObject(data)));
        return resp.toString();
    }

    /**
     * 获取菜单
     *
     * @param request  request
     * @param response response
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/menu/get", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, AccessTokenException, WeChatException {

        JSONObject resp = JSONObject.fromObject(menuService.get());
        return resp.toString();
    }

    /**
     * 删除默认菜单
     *
     * @param request  request
     * @param response response
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/menu/delete/default", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public String deleteDetault(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, AccessTokenException, WeChatException {

        JSONObject resp = JSONObject.fromObject(menuService.deleteDefault());
        return resp.toString();
    }

    /**
     * 删除条件菜单
     *
     * @param request  request
     * @param response response
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/menu/delete/condition", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public String deleteCondition(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "id", required = true) String id
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {

        JSONObject resp = JSONObject.fromObject(menuService.deleteCondition(id));
        return resp.toString();
    }

    /**
     * 测试菜单
     *
     * @param request
     * @param response
     * @param userId
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/menu/test", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public String test(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value = "userId", required = true) String userId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {

        JSONObject resp = JSONObject.fromObject(menuService.test(userId));
        return resp.toString();
    }
}
