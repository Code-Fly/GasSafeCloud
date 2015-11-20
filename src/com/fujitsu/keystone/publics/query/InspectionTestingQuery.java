/**
 * 
 */
package com.fujitsu.keystone.publics.query;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fujitsu.base.helper.Const;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.event.Event;
import com.fujitsu.keystone.publics.service.impl.MessageService;

/**
 * @author Barrie
 *
 */
public class InspectionTestingQuery extends Query {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fujitsu.keystone.publics.query.Query#execute(javax.servlet.http.
	 * HttpServletRequest, net.sf.json.JSONObject)
	 */
	@Override
	public String execute(HttpServletRequest request, JSONObject requestJson) {
		String respXml = null;
		// 发送方帐号
		String fromUserName = requestJson.getString(Event.FROM_USER_NAME);
		// 开发者微信号
		String toUserName = requestJson.getString(Event.TO_USER_NAME);

		String queryType = Query.SEPARATOR + Query.DISTRIBUTION_TRANSPORTATION + Query.SEPARATOR;
		String content = requestJson.getString("Content").trim();
		// 将搜索字符及后面的+、空格、-等特殊符号去掉
		String keyWord = content.replaceAll("^" + queryType + "[\\+ ~!@#%^-_=]?", "");

		TextMessage message = new TextMessage();

		message.setToUserName(fromUserName);
		message.setFromUserName(toUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
		message.setContent("正在查询 " + requestJson.getString(INSPECTION_TESTING) + ":" + keyWord);

		// 将消息对象转换成xml
		respXml = MessageService.messageToXml(message);
		return respXml;
	}
}
