package com.fujitsu.keystone.merchant.service.iface;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;

import java.util.Map;

/**
 * Created by Barrie on 15/11/21.
 */
public interface IPayService {
    Map<String, String> payRefund(Map<String, Object> data) throws ConnectionFailedException, WeChatException;
}
