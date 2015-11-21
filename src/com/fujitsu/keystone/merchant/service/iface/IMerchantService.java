/**
 * 
 */
package com.fujitsu.keystone.merchant.service.iface;

import java.util.Map;

/**
 * @author Barrie
 *
 */
public interface IMerchantService {
	String sendCoupon(Map<String, Object> data);

	Map<String, String> sendRedpack(Map<String, Object> data);

	Map<String, String> payRefund(Map<String, Object> data);
}
