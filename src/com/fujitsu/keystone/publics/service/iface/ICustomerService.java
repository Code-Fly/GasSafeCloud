/**
 *
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.keystone.publics.entity.customer.account.KfInfo;
import com.fujitsu.keystone.publics.entity.customer.message.CouponMessage;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;
import net.sf.json.JSONObject;

/**
 * @author Barrie
 */
public interface ICustomerService {
    JSONObject getAccountList(String accessToken) throws ConnectionFailedException, WeChatException;

    JSONObject getAccountListOnline(String accessToken) throws ConnectionFailedException, WeChatException;

    JSONObject getAccountAdd(String accessToken, KfInfo account) throws ConnectionFailedException, WeChatException;

    JSONObject getAccountDelete(String accessToken, KfInfo account) throws ConnectionFailedException, WeChatException;

    JSONObject getAccountUpdate(String accessToken, KfInfo account) throws ConnectionFailedException, WeChatException;

    JSONObject sendTextMessage(String accessToken, TextMessage message) throws ConnectionFailedException, WeChatException;

    JSONObject sendCouponMessage(String accessToken, CouponMessage message) throws ConnectionFailedException, WeChatException;
}
