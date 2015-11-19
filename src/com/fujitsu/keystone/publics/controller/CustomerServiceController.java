/**
 * 
 */
package com.fujitsu.keystone.publics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.entity.customer.message.CouponMessage;
import com.fujitsu.keystone.publics.entity.customer.message.WxCard;
import com.fujitsu.keystone.publics.service.iface.ICustomerService;
import com.fujitsu.keystone.publics.service.impl.CustomerService;

/**
 * @author Barrie
 *
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class CustomerServiceController extends BaseController {
	@Resource
	ICustomerService customerService;

	@RequestMapping(value = "/customerservice/coupon/send", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String send(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "touser", required = true) String touser,
			@RequestParam(value = "cardId", required = true) String cardId) throws ConnectionFailedException, AccessTokenException  {
		String at = KeystoneUtil.getAccessToken();
		
		CouponMessage message = new CouponMessage();
		message.setMsgtype(CustomerService.CUSTOMER_SERVICE_MESSAGE_TYPE_COUPON);
		message.setTouser(touser);
		WxCard coupon = new WxCard();
		coupon.setCard_id(cardId);
		message.setWxcard(coupon);

		JSONObject resp = customerService.sendCouponMessage(at, message);

		return resp.toString();
	}
}
