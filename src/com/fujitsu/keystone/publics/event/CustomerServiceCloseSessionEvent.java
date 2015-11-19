/**
 * 
 */
package com.fujitsu.keystone.publics.event;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.entity.customer.message.Text;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;
import com.fujitsu.keystone.publics.entity.push.response.TransferCustomerService;
import com.fujitsu.keystone.publics.service.impl.CustomerService;
import com.fujitsu.keystone.publics.service.impl.MessageService;

/**
 * @author Barrie
 *
 */
public class CustomerServiceCloseSessionEvent extends Event {
	@Override
	public String execute(HttpServletRequest request, Map<String, String> requestMap) throws ConnectionFailedException, AccessTokenException {
		String at = KeystoneUtil.getAccessToken();

		String respXml = null;
		// 发送方帐号
		String fromUserName = requestMap.get("FromUserName");
		// 开发者微信号
		String toUserName = requestMap.get("ToUserName");

		String kfAccount = requestMap.get("KfAccount");

		TransferCustomerService transferMessage = new TransferCustomerService();

		transferMessage.setToUserName(fromUserName);
		transferMessage.setFromUserName(toUserName);
		transferMessage.setCreateTime(new Date().getTime());
		transferMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_TRANSFER_CUSTOMER_SERVICE);

		// 将消息对象转换成xml
		respXml = MessageService.messageToXml(transferMessage);

		TextMessage message = new TextMessage();
		message.setMsgtype(CustomerService.CUSTOMER_SERVICE_MESSAGE_TYPE_TEXT);
		message.setTouser(fromUserName);
		Text t = new Text();
		StringBuffer buffer = new StringBuffer();
		buffer.append(kfAccount + " 祝您购物愉快。").append("\n");
		t.setContent(buffer.toString());
		message.setText(t);
		new CustomerService().sendTextMessage(at, message);

		return respXml;
	}

}
