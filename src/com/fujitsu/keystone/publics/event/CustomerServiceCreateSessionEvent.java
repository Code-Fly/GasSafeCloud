/**
 * 
 */
package com.fujitsu.keystone.publics.event;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.entity.customer.message.Text;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;
import com.fujitsu.keystone.publics.service.impl.CustomerService;

/**
 * @author Barrie
 *
 */
public class CustomerServiceCreateSessionEvent extends Event {
	@Override
	public String execute(HttpServletRequest request, Map<String, String> requestMap) throws ConnectionFailedException, AccessTokenException {
		String at = KeystoneUtil.getAccessToken();

		String respXml = null;
		// 发送方帐号
		String fromUserName = requestMap.get("FromUserName");

		String kfAccount = requestMap.get("KfAccount");

		TextMessage customerMsg = new TextMessage();
		customerMsg.setMsgtype(CustomerService.CUSTOMER_SERVICE_MESSAGE_TYPE_TEXT);
		customerMsg.setTouser(fromUserName);
		Text t = new Text();
		StringBuffer buffer = new StringBuffer();
		buffer.append(kfAccount + " 正在为您服务。").append("\n");
		t.setContent(buffer.toString());
		customerMsg.setText(t);
		new CustomerService().sendTextMessage(at, customerMsg);

		return respXml;
	}
}
