/**
 * 
 */
package com.fujitsu.keystone.publics.event;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fujitsu.base.helper.Const;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MessageService;

/**
 * @author Barrie
 *
 */
public class SubscribeEvent extends Event {

	@Override
	public String execute(HttpServletRequest request, Map<String, String> requestMap) {
		String respXml = null;
		// 发送方帐号
		String fromUserName = requestMap.get("FromUserName");
		// 开发者微信号
		String toUserName = requestMap.get("ToUserName");

		TextMessage textMessage = new TextMessage();

		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setContent("您好，欢迎关注" + Const.MERCHANT_NAME + "！");

		// 将消息对象转换成xml
		respXml = MessageService.messageToXml(textMessage);
		return respXml;
	}

}
