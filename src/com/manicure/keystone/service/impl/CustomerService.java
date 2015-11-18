/**
 * 
 */
package com.manicure.keystone.service.impl;

import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.manicure.base.helper.HttpClientUtil;
import com.manicure.base.service.BaseService;
import com.manicure.keystone.entity.customer.message.CouponMessage;
import com.manicure.keystone.entity.customer.message.TextMessage;
import com.manicure.keystone.entity.error.ErrorMsg;
import com.manicure.keystone.service.iface.ICustomerService;

/**
 * @author Barrie
 *
 */
@Service
public class CustomerService extends BaseService implements ICustomerService {
	public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_TEXT = "text";

	public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_IMAGE = "image";

	public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_VIDEO = "video";

	public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_MUSIC = "music";

	public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_NEWS = "news";

	public static final String CUSTOMER_SERVICE_MESSAGE_TYPE_COUPON = "wxcard";

	public JSONObject sendTextMessage(String accessToken, TextMessage message) {

		JSONObject response = sendMessage(accessToken, JSONObject.fromObject(message));
		return response;
	}

	public JSONObject sendCouponMessage(String accessToken, CouponMessage message) {

		JSONObject response = sendMessage(accessToken, JSONObject.fromObject(message));
		return response;
	}

	private JSONObject sendMessage(String accessToken, JSONObject message) {
		String url = URL_CUSTOMER_SERVICE_MESSAGE_SEND.replace("ACCESS_TOKEN", accessToken);

		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", message.toString());

		if (null == response) {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrcode("-1");
			errMsg.setErrmsg("server is busy");

			return JSONObject.fromObject(errMsg);
		}

		return response;
	}
}
