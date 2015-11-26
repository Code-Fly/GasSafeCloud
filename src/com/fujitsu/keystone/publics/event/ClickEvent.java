/**
 *
 */
package com.fujitsu.keystone.publics.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fujitsu.keystone.publics.query.Query;
import net.sf.json.JSONObject;

import com.fujitsu.keystone.publics.entity.push.response.Article;
import com.fujitsu.keystone.publics.entity.push.response.NewsMessage;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MenuService;
import com.fujitsu.keystone.publics.service.impl.MessageService;

/**
 * @author Barrie
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
        textMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
        // 事件KEY值，与创建菜单时的key值对应
        String eventKey = requestJson.getString(EVENT_KEY);
        // 根据key值判断用户点击的按钮
        // 充装存储
        if (eventKey.equals(MenuService.GL_CZCC)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("充装存储查询").append(ENTER);
            buffer.append(ENTER);
            buffer.append("查询该单位气瓶充装、存储许可信息和本单位作业人员信息。").append(ENTER);
            buffer.append("输入格式:").append(ENTER);
            buffer.append(Query.SEPARATOR + Query.FILLING_STORAGE + Query.SEPARATOR + Query.QUERY_DETAIL + Query.SEPARATOR + "单位全名").append(ENTER);
            buffer.append(ENTER);
            buffer.append("单位名称查询(支持模糊查询)").append(ENTER);
            buffer.append("输入格式:").append(ENTER);
            buffer.append(Query.SEPARATOR + Query.FILLING_STORAGE + Query.SEPARATOR + Query.QUERY_LIST + Query.SEPARATOR + "查询名").append(ENTER);
            textMessage.setContent(buffer.toString());
            respXml = MessageService.messageToXml(textMessage);
        }
        // 配送运输
        else if (eventKey.equals(MenuService.GL_PSYS)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("配送运输查询").append(ENTER);
            buffer.append(ENTER);
            buffer.append("查询该单位燃气配送、运输许可信息和从业人员信息。").append(ENTER);
            buffer.append("输入格式:").append(ENTER);
            buffer.append(Query.SEPARATOR + Query.DISTRIBUTION_TRANSPORTATION + Query.SEPARATOR + Query.QUERY_DETAIL + Query.SEPARATOR + "单位全名").append(ENTER);
            buffer.append(ENTER);
            buffer.append("单位名称查询(支持模糊查询)").append(ENTER);
            buffer.append("输入格式:").append(ENTER);
            buffer.append(Query.SEPARATOR + Query.DISTRIBUTION_TRANSPORTATION + Query.SEPARATOR + Query.QUERY_LIST + Query.SEPARATOR + "查询名").append(ENTER);
            textMessage.setContent(buffer.toString());
            respXml = MessageService.messageToXml(textMessage);
        }
        // 检验检测
        else if (eventKey.equals(MenuService.GL_JYJC)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("检验检测查询").append(ENTER);
            buffer.append(ENTER);
            buffer.append("查询该单位气瓶检验信息和检验人员信息。").append(ENTER);
            buffer.append("输入格式:").append(ENTER);
            buffer.append(Query.SEPARATOR + Query.INSPECTION_TESTING + Query.SEPARATOR + Query.QUERY_DETAIL + Query.SEPARATOR + "单位全名").append(ENTER);
            buffer.append(ENTER);
            buffer.append("单位名称查询(支持模糊查询)").append(ENTER);
            buffer.append("输入格式:").append(ENTER);
            buffer.append(Query.SEPARATOR + Query.INSPECTION_TESTING + Query.SEPARATOR + Query.QUERY_LIST + Query.SEPARATOR + "查询名").append(ENTER);
            textMessage.setContent(buffer.toString());
            respXml = MessageService.messageToXml(textMessage);
        } else {
            textMessage.setContent("功能尚未开放，敬请期待！" + eventKey);
            respXml = MessageService.messageToXml(textMessage);
        }
        return respXml;
    }

}
