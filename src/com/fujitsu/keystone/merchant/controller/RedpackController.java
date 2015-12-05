package com.fujitsu.keystone.merchant.controller;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.merchant.service.iface.IRedpackService;
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
public class RedpackController extends BaseController {
    @Resource
    IRedpackService redpackService;

    @RequestMapping(value = "/merchant/redpack/send/{openId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendRedpack(HttpServletRequest request, HttpServletResponse response, @PathVariable String openId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("nonce_str", KeystoneUtil.getNonceStr());// 随机字符串
        map.put("mch_billno", KeystoneUtil.getTradeNo(Const.MerchantPlatform.MCH_ID));// 商户订单
        map.put("mch_id", Const.MerchantPlatform.MCH_ID);// 商户号
        map.put("wxappid", Const.WECHART_APP_ID);// 商户appid
        map.put("nick_name", Const.WECHART_NAME);// 提供方名称
        map.put("send_name", Const.WECHART_NAME);// 用户名
        map.put("re_openid", openId);// 用户openid
        map.put("total_amount", 100);// 付款金额
        map.put("min_value", 100);// 最小红包
        map.put("max_value", 100);// 最大红包
        map.put("total_num", 1);// 红包发送总人数
        map.put("wishing", "新年快乐");// 红包祝福语
        map.put("client_ip", "127.0.0.1");// ip地址
        map.put("act_name", "过年红包");// 活动名称
        map.put("remark", "新年新气象");// 备注
        map.put("sign", KeystoneUtil.createSign(map, Const.MerchantPlatform.MCH_SECRET));// 签名

        Map<String, String> resp = redpackService.sendRedpack(map);
        if ("FAIL".equals(resp.get("result_code"))) {
            logger.error(resp.toString());
        }

        return resp.toString();

    }
}
