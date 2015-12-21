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
    public JSONObject sendTextMessage(TextMessage message) throws ConnectionFailedException, WeChatException, AccessTokenException {
        JSONObject response = sendMessage(JSONObject.fromObject(message));
        return response;
    }

    private JSONObject sendMessage(JSONObject message) throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_CUSTOMER_SERVICE_MESSAGE_SEND;

        String response = WeChatClientUtil.post(url, message.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }


}
