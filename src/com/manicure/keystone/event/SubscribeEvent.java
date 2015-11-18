/**
 * 
 */
package com.manicure.keystone.event;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.manicure.keystone.entity.response.TextMessage;
import com.manicure.keystone.service.impl.MessageService;

/**
 * @author Barrie
 *
 */
public class SubscribeEvent extends Event {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.manicure.keystone.event.Event#execute(java.util.Map)
	 */
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
		textMessage.setContent("您好，欢迎关注米兰美甲艺社！体验生活，从这里开始！");

		// 将消息对象转换成xml
		respXml = MessageService.messageToXml(textMessage);
		return respXml;
	}

}
