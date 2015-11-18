/**
 * 
 */
package com.manicure.keystone.service.impl;

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

import com.manicure.base.helper.Const;
import com.manicure.base.helper.HttpClientUtil;
import com.manicure.base.helper.MD5Util;
import com.manicure.base.helper.XmlUtil;
import com.manicure.base.service.BaseService;
import com.manicure.keystone.service.iface.ICoreService;
import com.manicure.keystone.service.iface.IMerchantService;

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
