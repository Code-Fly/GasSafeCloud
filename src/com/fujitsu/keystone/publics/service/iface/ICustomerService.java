/**
 *
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;
import net.sf.json.JSONObject;

/**
 * @author Barrie
 */
public interface ICustomerService {

    JSONObject sendTextMessage(TextMessage message) throws ConnectionFailedException, WeChatException, AccessTokenException;

}
