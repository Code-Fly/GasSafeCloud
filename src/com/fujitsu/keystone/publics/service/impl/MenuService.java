/**
 *
 */
package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.helper.WeChatClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.service.iface.IMenuService;
import com.fujitsu.keystone.publics.service.iface.IMessageService;
import net.sf.json.JSONObject;
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

    public static final String FW_RSQP = "FW_RSQP";
    public static final String FW_YQAQ = "FW_YQAQ";
    public static final String FW_ZXTS = "FW_ZXTS";

    @Resource
    IMessageService messageService;

    /**
     * @param accessToken
     * @param json
     * @return
     * @throws ConnectionFailedException
     */
    public JSONObject create(String accessToken, JSONObject json) throws ConnectionFailedException, WeChatException {

        // 拼装创建菜单的url
        String url = Const.PublicPlatform.URL_MENU_CREATE.replace("ACCESS_TOKEN", accessToken);

        String response = WeChatClientUtil.post(url, json.toString(), "UTF-8");

        return JSONObject.fromObject(response);
    }

    /**
     * @param accessToken
     * @return
     * @throws ConnectionFailedException
     */
    public JSONObject get(String accessToken) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_MENU_GET.replace("ACCESS_TOKEN", accessToken);

        String response = WeChatClientUtil.get(url, "UTF-8");

        return JSONObject.fromObject(response);
    }

    /**
     * @param accessToken
     * @return
     * @throws ConnectionFailedException
     */
    public JSONObject delete(String accessToken) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_MENU_DELETE.replace("ACCESS_TOKEN", accessToken);

        String response = WeChatClientUtil.get(url, "UTF-8");

        return JSONObject.fromObject(response);
    }


}
