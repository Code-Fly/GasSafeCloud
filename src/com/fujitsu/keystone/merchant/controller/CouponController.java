package com.fujitsu.keystone.merchant.controller;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.merchant.service.iface.ICouponService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Barrie on 15/11/21.
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class CouponController extends BaseController {
    @Resource
    ICouponService couponService;

    @RequestMapping(value = "/merchant/coupon/send/{couponStockId}/{openId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendCoupon(HttpServletRequest request, HttpServletResponse response, @PathVariable String couponStockId, @PathVariable String openId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("appid", Const.APP_ID);
        map.put("coupon_stock_id", couponStockId);
        map.put("mch_id", Const.MerchantPlatform.MCH_ID);
        map.put("nonce_str", KeystoneUtil.getNonceStr());
        map.put("openid", openId);
        map.put("openid_count", 1);
        map.put("partner_trade_no", KeystoneUtil.getTradeNo(Const.MerchantPlatform.MCH_ID));
        map.put("sign", KeystoneUtil.createSign(map, Const.MerchantPlatform.MCH_SECRET));

        String resp = couponService.sendCoupon(map);
        logger.info(resp);
        return resp.toString();

    }
}
