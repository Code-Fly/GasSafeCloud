package com.fujitsu.keystone.merchant.service.iface;

import java.util.Map;

/**
 * Created by Barrie on 15/11/21.
 */
public interface IPayService {
    Map<String, String> payRefund(Map<String, Object> data);
}
