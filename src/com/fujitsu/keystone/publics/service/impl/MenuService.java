/**
 *
 */
package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.WeChatClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.service.iface.IMenuService;
import com.fujitsu.keystone.publics.service.iface.IMessageService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.CharEncoding;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Barrie
 */
@Service
public class MenuService extends BaseService implements IMenuService {

    public static final String QP_SFCX = "QP_SFCX";
    public static final String QP_GZJL = "QP_GZJL";
    public static final String QP_LZGZ = "QP_LZGZ";
    public static final String QP_SMSY = "QP_SMSY";
    public static final String QP_AQDW = "QP_AQDW";

    public static final String GL_CYQP = "GL_CYQP";
    public static final String GL_CZCC = "GL_CZCC";
    public static final String GL_PSYS = "GL_PSYS";
    public static final String GL_JYJC = "GL_JYJC";
    public static final String GL_DWCX = "GL_DWCX";
    public static final String GL_YXT = "GL_YXT";

    public static final String FW_RSQP = "FW_RSQP";
    public static final String FW_YQAQ = "FW_YQAQ";
    public static final String FW_ZXTS = "FW_ZXTS";

    @Resource
    IMessageService messageService;


    @Override
    public JSONObject createDefault(JSONObject json) throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_MENU_CREATE_DEFAULT;

        String response = WeChatClientUtil.post(url, json.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    @Override
    public JSONObject createCondition(JSONObject json) throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_MENU_CREATE_CONDITION;

        String response = WeChatClientUtil.post(url, json.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    @Override
    public JSONObject get() throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_MENU_GET;

        String response = WeChatClientUtil.get(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    @Override
    public JSONObject deleteDefault() throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_MENU_DELETE_CONDITION;

        String response = WeChatClientUtil.get(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    @Override
    public JSONObject deleteCondition(String id) throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_MENU_DELETE_DEFAULT;

        JSONObject param = new JSONObject();
        param.put("menuid", id);

        String response = WeChatClientUtil.post(url, param.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    @Override
    public JSONObject test(String userId) throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_MENU_TEST;

        JSONObject param = new JSONObject();
        param.put("user_id", userId);

        String response = WeChatClientUtil.post(url, param.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }
}
