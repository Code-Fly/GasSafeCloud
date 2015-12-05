package com.fujitsu.keystone.merchant.controller;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.merchant.service.iface.IPayService;
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
public class PayController extends BaseController {
    @Resource
    IPayService payService;

    @RequestMapping(value = "/merchant/pay/refund/{tradeId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String payRefund(HttpServletRequest request, HttpServletResponse response, @PathVariable String tradeId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("appid", Const.WECHART_APP_ID);
        map.put("mch_id", Const.MerchantPlatform.MCH_ID);
        map.put("nonce_str", KeystoneUtil.getNonceStr());
        map.put("op_user_id", Const.MerchantPlatform.MCH_ID);
        map.put("transaction_id", tradeId);
        // map.put("out_trade_no", tradeId);
        map.put("out_refund_no", KeystoneUtil.getTradeNo(Const.MerchantPlatform.MCH_ID));
        map.put("refund_fee", 1);
        map.put("total_fee", 1);

        map.put("sign", KeystoneUtil.createSign(map, Const.MerchantPlatform.MCH_SECRET));

        Map<String, String> resp = payService.payRefund(map);
        return resp.toString();
    }
}
