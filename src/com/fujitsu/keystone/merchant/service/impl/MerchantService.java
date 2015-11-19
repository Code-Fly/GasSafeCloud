/**
 * 
 */
package com.fujitsu.keystone.merchant.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fujitsu.base.helper.Const;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.helper.MD5Util;
import com.fujitsu.base.helper.XmlUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.merchant.service.iface.IMerchantService;
import com.fujitsu.keystone.publics.service.iface.ICoreService;

/**
 * @author Barrie
 *
 */
@Service
public class MerchantService extends BaseService implements IMerchantService {

	@Resource
	ICoreService coreService;

	/**
	 * 
	 */
	public String sendCoupon(Map<String, Object> data) {
		String url = Const.URL_MERCHANT_COUPON_SEND;

		String response = HttpClientUtil.doHttpsPost(url, XmlUtil.toXML(data), "UTF-8");

		return response;
	}

	public Map<String, String> sendRedpack(Map<String, Object> data) {
		String url = Const.URL_MERCHANT_REDPACK_SEND;

		String response = HttpClientUtil.doHttpsPost(url, XmlUtil.toXMLWithCDATA(data), "UTF-8");

		return XmlUtil.parseXml(response);
	}
	
	public Map<String, String> payRefund(Map<String, Object> data) {
		String url = Const.URL_MERCHANT_PAY_REFUND;

		String response = HttpClientUtil.doHttpsPost(url, XmlUtil.toXML(data), "UTF-8");

		return XmlUtil.parseXml(response);
	}

	
}
