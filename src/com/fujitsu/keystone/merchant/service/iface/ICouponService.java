package com.fujitsu.keystone.merchant.service.iface;

import java.util.Map;

/**
 * Created by Barrie on 15/11/21.
 */
public interface ICouponService {
    String sendCoupon(Map<String, Object> data);
}
