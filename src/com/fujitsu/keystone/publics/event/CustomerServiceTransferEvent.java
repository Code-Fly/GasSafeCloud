/**
 * 
 */
package com.fujitsu.keystone.publics.event;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

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
public class CustomerServiceTransferEvent extends Event {

	@Override
	public String execute(HttpServletRequest request, JSONObject requestJson) throws ConnectionFailedException, AccessTokenException, WeChatException, JMSException, UnsupportedEncodingException, GasSafeException {
		String at = KeystoneUtil.getAccessToken();
		
		String respXml = null;
		// 发送方帐号
		String fromUserName = requestJson.getString(FROM_USER_NAME);
		// 开发者微信号
		String toUserName = requestJson.getString(TO_USER_NAME);

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
		buffer.append("请稍后，我们的客服人员马上会接待您。").append(Const.LINE_SEPARATOR);
		t.setContent(buffer.toString());
		message.setText(t);
		new CustomerService().sendTextMessage(at, message);

		super.execute(request, requestJson);
		return respXml;
	}

}
