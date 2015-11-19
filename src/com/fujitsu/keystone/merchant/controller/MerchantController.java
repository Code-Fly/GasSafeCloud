/**
 * 
 */
package com.fujitsu.keystone.merchant.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.helper.Const;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.merchant.service.impl.MerchantService;
import com.fujitsu.keystone.publics.service.impl.CoreService;

/**
 * @author Barrie
 *
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class MerchantController extends BaseController {
	@Resource
	CoreService coreService;
	@Resource
	MerchantService merchantService;

	@RequestMapping(value = "/merchant/redpack/send/{openId}")
	@ResponseBody
	public String sendRedpack(HttpServletRequest request, HttpServletResponse response, @PathVariable String openId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("nonce_str", KeystoneUtil.getNonceStr());// 随机字符串
		map.put("mch_billno", KeystoneUtil.getTradeNo(Const.MCH_ID));// 商户订单
		map.put("mch_id", Const.MCH_ID);// 商户号
		map.put("wxappid", Const.APP_ID);// 商户appid
		map.put("nick_name", Const.MERCHANT_NAME);// 提供方名称
		map.put("send_name", Const.MERCHANT_NAME);// 用户名
		map.put("re_openid", openId);// 用户openid
		map.put("total_amount", 100);// 付款金额
		map.put("min_value", 100);// 最小红包
		map.put("max_value", 100);// 最大红包
		map.put("total_num", 1);// 红包发送总人数
		map.put("wishing", "新年快乐");// 红包祝福语
		map.put("client_ip", "127.0.0.1");// ip地址
		map.put("act_name", "过年红包");// 活动名称
		map.put("remark", "新年新气象");// 备注
		map.put("sign", KeystoneUtil.createSign(map, Const.MCH_SECRET));// 签名

		Map<String, String> resp = merchantService.sendRedpack(map);
		if ("FAIL".equals(resp.get("result_code"))) {
			logger.error(resp.toString());
		}

		return resp.toString();

	}

	@RequestMapping(value = "/merchant/coupon/send/{couponStockId}/{openId}")
	@ResponseBody
	public String sendCoupon(HttpServletRequest request, HttpServletResponse response, @PathVariable String couponStockId, @PathVariable String openId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("appid", Const.APP_ID);
		map.put("coupon_stock_id", couponStockId);
		map.put("mch_id", Const.MCH_ID);
		map.put("nonce_str", KeystoneUtil.getNonceStr());
		map.put("openid", openId);
		map.put("openid_count", 1);
		map.put("partner_trade_no", KeystoneUtil.getTradeNo(Const.MCH_ID));
		map.put("sign", KeystoneUtil.createSign(map, Const.MCH_SECRET));

		String resp = merchantService.sendCoupon(map);
		logger.info(resp);
		return resp.toString();

	}

	@RequestMapping(value = "/merchant/pay/refund/{tradeId}")
	@ResponseBody
	public String payRefund(HttpServletRequest request, HttpServletResponse response, @PathVariable String tradeId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("appid", Const.APP_ID);
		map.put("mch_id", Const.MCH_ID);
		map.put("nonce_str", KeystoneUtil.getNonceStr());
		map.put("op_user_id", Const.MCH_ID);
		map.put("transaction_id", tradeId);
		// map.put("out_trade_no", tradeId);
		map.put("out_refund_no", KeystoneUtil.getTradeNo(Const.MCH_ID));
		map.put("refund_fee", 1);
		map.put("total_fee", 1);

		map.put("sign", KeystoneUtil.createSign(map, Const.MCH_SECRET));

		Map<String, String> resp = merchantService.payRefund(map);
		return resp.toString();
	}
}
