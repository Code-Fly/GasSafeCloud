package com.fujitsu.keystone.merchant.service.iface;

import java.util.Map;

/**
 * Created by Barrie on 15/11/21.
 */
public interface IRedpackService {
    Map<String, String> sendRedpack(Map<String, Object> data);
}
