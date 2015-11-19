/**
 * 
 */
package com.fujitsu.keystone.publics.event;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fujitsu.base.helper.Const;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MessageService;

/**
 * @author Barrie
 *
 */
public class SubscribeEvent extends Event {

	@Override
	public String execute(HttpServletRequest request, JSONObject requestJson) {
		String respXml = null;
		// 发送方帐号
		String fromUserName = requestJson.getString(Event.FROM_USER_NAME);
		// 开发者微信号
		String toUserName = requestJson.getString(Event.TO_USER_NAME);

		TextMessage message = new TextMessage();

		message.setToUserName(fromUserName);
		message.setFromUserName(toUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
		message.setContent("您好，欢迎关注" + Const.MERCHANT_NAME + "！");

		// 将消息对象转换成xml
		respXml = MessageService.messageToXml(message);
		return respXml;
	}

}
