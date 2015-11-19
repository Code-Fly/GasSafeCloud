package com.fujitsu.keystone.publics.event;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MenuService;
import com.fujitsu.keystone.publics.service.impl.MessageService;

public class ScancodeWaitmsgEvent extends Event {

	/**
	 * @throws AccessTokenException
	 * @throws ConnectionFailedException
	 * 
	 */
	public String execute(HttpServletRequest request, JSONObject requestJson) throws ConnectionFailedException, AccessTokenException {
		String respXml = null;

		String fromUserName = requestJson.getString(FROM_USER_NAME);
		String toUserName = requestJson.getString(TO_USER_NAME);
		JSONObject scanCodeInfo = JSONObject.fromObject(requestJson.get(SCAN_CODE_INFO));
		String scanResult = scanCodeInfo.getString(SCAN_RESULT);

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
		StringBuffer buffer = new StringBuffer();
		// 身份查询
		if (MenuService.QP_SFCX.equals(requestJson.get(EVENT_KEY))) {
			buffer.append("气瓶使用证编号:");
			buffer.append(messArray[0]);
			buffer.append(ENTER);
			buffer.append("气瓶注册代码:");
			buffer.append(messArray[1]);
			buffer.append(ENTER);
			buffer.append("气瓶充装单位(编号):");
			buffer.append(messArray[3]);
			buffer.append(ENTER);
			buffer.append("气瓶编号:");
			buffer.append(messArray[3]);
			buffer.append(ENTER);
			buffer.append("出厂日期:");
			buffer.append(messArray[3]);
			buffer.append(ENTER);
			buffer.append("报废日期:");
			buffer.append(messArray[3]);
			buffer.append(ENTER);
		}
		textMessage.setContent(buffer.toString());

		// 将消息对象转换成xml
		respXml = MessageService.messageToXml(textMessage);

		return respXml;

	}
}
