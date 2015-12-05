package com.fujitsu.keystone.merchant.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.helper.WeChatClientUtil;
import com.fujitsu.base.helper.XmlUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.merchant.service.iface.ICouponService;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Barrie on 15/11/21.
 */
@Service
public class CouponService extends BaseService implements ICouponService {
    @Resource
    ICoreService coreService;

    /**
     * @param data
     * @return
     */
    public String sendCoupon(Map<String, Object> data) throws ConnectionFailedException, WeChatException {
        String url = Const.MerchantPlatform.URL_MERCHANT_COUPON_SEND;

        String response = WeChatClientUtil.doPost(url, XmlUtil.toXML(data), "UTF-8");

        return response;
    }
}
