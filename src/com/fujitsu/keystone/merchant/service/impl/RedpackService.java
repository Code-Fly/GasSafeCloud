package com.fujitsu.keystone.merchant.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.helper.WeChatClientUtil;
import com.fujitsu.base.helper.XmlUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.merchant.service.iface.IRedpackService;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Barrie on 15/11/21.
 */
@Service
public class RedpackService extends BaseService implements IRedpackService {
    @Resource
    ICoreService coreService;

    public Map<String, String> sendRedpack(Map<String, Object> data) throws ConnectionFailedException, WeChatException {
        String url = Const.MerchantPlatform.URL_MERCHANT_REDPACK_SEND;

        String response = WeChatClientUtil.post(url, XmlUtil.toXMLWithCDATA(data), "UTF-8");

        return XmlUtil.parseXml(response);
    }
}
