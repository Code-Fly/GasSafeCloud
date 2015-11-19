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
		String at = KeystoneUtil.getAccessToken();

		String respXml = null;
		// 发送方帐号
		String fromUserName = requestMap.get("FromUserName");
		// 开发者微信号
		String toUserName = requestMap.get("ToUserName");

		String scanResult = requestMap.get(SCAN_RESULT);

		TextMessage textMessage = new TextMessage();

		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);

		/**
		 * 处理message 推送给用户的message
		 * [QP02001,132020000001,AG,323232,2015年09月,2045年09月]气瓶安全云www.qpsafe.cn
		 */
		int lastIndex = scanResult.lastIndexOf("]");
		// QP02001,132020000001,AG,323232,2015年09月,2045年09月
		String tmp = scanResult.substring(1, lastIndex);
		// [QP02001,132020000001,AG,323232,2015年09月,2045年09月]
		String[] messArray = tmp.split(",");
		StringBuffer mess = new StringBuffer();
		// 身份查询
		if (MenuService.QP_SFCX.equals(requestMap.get(EVENT_KEY))) {
			mess.append("气瓶使用证编号:").append(messArray[0]).append(ENTER).append("气瓶注册代码:").append(messArray[1]).append(ENTER).append("气瓶充装单位(编号):").append(messArray[3]).append(ENTER).append("气瓶编号:")
					.append(messArray[3]).append(ENTER).append("出厂日期:").append(messArray[3]).append(ENTER).append("报废日期:").append(messArray[3]).append(ENTER);
		}
		textMessage.setContent(mess.toString());

		// 将消息对象转换成xml
		respXml = MessageService.messageToXml(textMessage);

		return respXml;

	}
}
