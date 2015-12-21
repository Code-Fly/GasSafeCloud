/**
 * 
 */
package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.FileUtil;
import com.fujitsu.base.helper.WeChatClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.entity.account.WeChatUserInfo;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IUserService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.CharEncoding;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Barrie
 */
@Service
public class UserService extends BaseService implements IUserService {
    @Resource
    ICoreService coreService;


    /**
     * 获取用户信息
     *
     * @param openId 用户标识
     * @return WeixinUserInfo
     * @throws ConnectionFailedException
     */
    @Override
    public JSONObject getWeChatUserInfo(String openId) throws ConnectionFailedException, WeChatException, AccessTokenException {
        // WeChatUserInfo wechatUserInfo = null;

        String url = Const.PublicPlatform.URL_USER_GET_INFO.replace("OPENID", openId);
        // 获取用户信息
        String response = WeChatClientUtil.post(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @param openId
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject getWeChatUserInfo(HttpServletRequest request, String openId) throws ConnectionFailedException, WeChatException, AccessTokenException {

        JSONObject resp = getWeChatUserInfo(openId);
        if (resp.containsKey("errcode")) {
            logger.error(resp.toString());
            return resp;
        }
        WeChatUserInfo weUserInfo = (WeChatUserInfo) JSONObject.toBean(resp, WeChatUserInfo.class);
        String headimgurl = Const.getServerUrl(request) + FileUtil.getWeChatImage(weUserInfo.getHeadimgurl() + "?wx_fmt=jpeg", FileUtil.CATEGORY_USER, weUserInfo.getOpenid(), false);
        weUserInfo.setHeadimgurl(headimgurl);
        return JSONObject.fromObject(weUserInfo);
    }


}
