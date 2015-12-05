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
import com.fujitsu.keystone.publics.entity.customer.message.CouponMessage;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;
import com.fujitsu.keystone.publics.service.iface.ICustomerService;
import net.sf.json.JSONObject;
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

    public JSONObject sendTextMessage(String accessToken, TextMessage message) throws ConnectionFailedException, WeChatException {

        JSONObject response = sendMessage(accessToken, JSONObject.fromObject(message));
        return response;
    }

    public JSONObject sendCouponMessage(String accessToken, CouponMessage message) throws ConnectionFailedException, WeChatException {

        JSONObject response = sendMessage(accessToken, JSONObject.fromObject(message));
        return response;
    }

    private JSONObject sendMessage(String accessToken, JSONObject message) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_CUSTOMER_SERVICE_MESSAGE_SEND.replace("ACCESS_TOKEN", accessToken);

        String response = WeChatClientUtil.doPost(url, message.toString(), "UTF-8");

        return JSONObject.fromObject(response);
    }
}
