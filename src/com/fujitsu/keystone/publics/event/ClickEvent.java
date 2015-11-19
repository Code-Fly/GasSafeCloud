/**
 * 
 */
package com.fujitsu.keystone.publics.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fujitsu.keystone.publics.entity.push.response.Article;
import com.fujitsu.keystone.publics.entity.push.response.NewsMessage;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MenuService;
import com.fujitsu.keystone.publics.service.impl.MessageService;

/**
 * @author Barrie
 *
 */
public class ClickEvent extends Event {
	public static final String EVENT_KEY = "EventKey";

	@Override
	public String execute(HttpServletRequest request, JSONObject requestJson) {
		String respXml = null;
		// 发送方帐号
		String fromUserName = requestJson.getString(FROM_USER_NAME);
		// 开发者微信号
		String toUserName = requestJson.getString(TO_USER_NAME);

		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(Event.RESP_MESSAGE_TYPE_TEXT);
		// 事件KEY值，与创建菜单时的key值对应
		String eventKey = requestJson.getString(EVENT_KEY);
		// 根据key值判断用户点击的按钮
		if (eventKey.equals(MenuService.FW_RSQP)) {
			Article article = new Article();
			article.setTitle("开源中国");
			article.setDescription("开源中国社区成立于2008年8月，是目前中国最大的开源技术社区。\n\n开源中国的目的是为中国的IT技术人员提供一个全面的、快捷更新的用来检索开源软件以及交流开源经验的平台。\n\n经过不断的改进,目前开源中国社区已经形成了由开源软件库、代码分享、资讯、讨论区和博客等几大频道内容。");
			article.setPicUrl("");
			article.setUrl("http://m.oschina.net");
			List<Article> articleList = new ArrayList<Article>();
			articleList.add(article);
			// 创建图文消息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(Event.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respXml = MessageService.messageToXml(newsMessage);
		} else if (eventKey.equals(MenuService.FW_YQAQ)) {
			textMessage.setContent("ITeye即创办于2003年9月的JavaEye,从最初的以讨论Java技术为主的技术论坛，已经逐渐发展成为涵盖整个软件开发领域的综合性网站。\n\nhttp://www.iteye.com");
			respXml = MessageService.messageToXml(textMessage);
		} else {
			textMessage.setContent("功能尚未开放，敬请期待！" + eventKey);
			respXml = MessageService.messageToXml(textMessage);
		}
		return respXml;
	}

}
