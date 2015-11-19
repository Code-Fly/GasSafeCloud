package com.fujitsu.keystone.publics.event;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.CustomerService;
import com.fujitsu.keystone.publics.service.impl.MenuService;
import com.fujitsu.keystone.publics.service.impl.MessageService;

public class ScancodeWaitmsgEvent extends Event {

	/**
	 * @throws AccessTokenException
	 * @throws ConnectionFailedException
	 * 
	 */
	public String execute(HttpServletRequest request, Map<String, String> requestMap) throws ConnectionFailedException, AccessTokenException {
		String respXml = null;

		String fromUserName = requestMap.get(FROM_USER_NAME);
		String toUserName = requestMap.get(TO_USER_NAME);
		String scanResult = requestMap.get(SCAN_RESULT);
		String scanCodeInfo = requestMap.get(SCAN_CODE_INFO);

		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
		
		System.out.println(scanResult);
		System.out.println(scanCodeInfo);
		/**
		 * 处理message 推送给用户的message
		 * [QP02001,132020000001,AG,323232,2015年09月,2045年09月]气瓶安全云www.qpsafe.cn
		 */
		int lastIndex = scanResult.lastIndexOf("]");
		// QP02001,132020000001,AG,323232,2015年09月,2045年09月
		String tmp = scanResult.substring(1, lastIndex);
		// [QP02001,132020000001,AG,323232,2015年09月,2045年09月]
		String[] messArray = tmp.split(",");
		StringBuffer buffer = new StringBuffer();
		// 身份查询
		if (MenuService.QP_SFCX.equals(requestMap.get(EVENT_KEY))) {
			buffer.append("气瓶使用证编号:");
			buffer.append(messArray[0]);
			buffer.append(ENTER).append("气瓶注册代码:");
			buffer.append(messArray[1]);
			buffer.append(ENTER);
			buffer.append("气瓶充装单位(编号):");
			buffer.append(messArray[3]);
			buffer.append(ENTER).append("气瓶编号:");
			buffer.append(messArray[3]);
			buffer.append(ENTER).append("出厂日期:");
			buffer.append(messArray[3]);
			buffer.append(ENTER).append("报废日期:");
			buffer.append(messArray[3]);
			buffer.append(ENTER);
		}
		textMessage.setContent(buffer.toString());

		// 将消息对象转换成xml
		respXml = MessageService.messageToXml(textMessage);

		return respXml;

	}
}
