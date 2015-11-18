/**
 * 
 */
package com.manicure.keystone.service.iface;

import java.util.Map;

/**
 * @author Barrie
 *
 */
public interface IMerchantService {
	public String sendCoupon(Map<String, Object> data);

	public Map<String, String> sendRedpack(Map<String, Object> data);

	public Map<String, String> payRefund(Map<String, Object> data);
}
