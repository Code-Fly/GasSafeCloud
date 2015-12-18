/**
 *
 */
package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.WeChatClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.entity.customer.account.KfInfo;
import com.fujitsu.keystone.publics.entity.customer.message.CouponMessage;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;
import com.fujitsu.keystone.publics.service.iface.ICustomerService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.CharEncoding;
import org.springframework.stereotype.Service;

/**
 * @author Barrie
 */
@Service
public class CustomerService extends BaseService implements ICustomerService {
    public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_TEXT = "text";

    public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_IMAGE = "image";

    public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_VIDEO = "video";

    public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_MUSIC = "music";

    public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_NEWS = "news";

    public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_COUPON = "wxcard";

    @Override
    public JSONObject getAccountList(String accessToken) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_CUSTOMER_SERVICE_KF_LIST.replace("ACCESS_TOKEN", accessToken);

        String response = WeChatClientUtil.get(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    @Override
    public JSONObject getAccountListOnline(String accessToken) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_CUSTOMER_SERVICE_KF_LIST_ONLINE.replace("ACCESS_TOKEN", accessToken);

        String response = WeChatClientUtil.get(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    @Override
    public JSONObject getAccountAdd(String accessToken, KfInfo account) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_CUSTOMER_SERVICE_KF_ADD.replace("ACCESS_TOKEN", accessToken);

        String response = WeChatClientUtil.post(url, JSONObject.fromObject(account).toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    @Override
    public JSONObject getAccountDelete(String accessToken, KfInfo account) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_CUSTOMER_SERVICE_KF_DELETE.replace("ACCESS_TOKEN", accessToken).replace("KFACCOUNT", account.getKf_account());

        String response = WeChatClientUtil.get(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    @Override
    public JSONObject getAccountUpdate(String accessToken, KfInfo account) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_CUSTOMER_SERVICE_KF_UPDATE.replace("ACCESS_TOKEN", accessToken);

        String response = WeChatClientUtil.post(url, JSONObject.fromObject(account).toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    @Override
    public JSONObject sendTextMessage(String accessToken, TextMessage message) throws ConnectionFailedException, WeChatException {
        JSONObject response = sendMessage(accessToken, JSONObject.fromObject(message));
        return response;
    }

    @Override
    public JSONObject sendCouponMessage(String accessToken, CouponMessage message) throws ConnectionFailedException, WeChatException {
        JSONObject response = sendMessage(accessToken, JSONObject.fromObject(message));
        return response;
    }

    private JSONObject sendMessage(String accessToken, JSONObject message) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_CUSTOMER_SERVICE_MESSAGE_SEND.replace("ACCESS_TOKEN", accessToken);

        String response = WeChatClientUtil.post(url, message.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }


}
