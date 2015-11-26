package com.fujitsu.keystone.publics.query;

import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.event.Event;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Barrie on 15/11/26.
 */
public class CompanyListQuery extends Query {
    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) {
        String respXml = null;
        // 发送方帐号
        String fromUserName = requestJson.getString(Event.FROM_USER_NAME);
        // 开发者微信号
        String toUserName = requestJson.getString(Event.TO_USER_NAME);

        String content = requestJson.getString("Content").trim();

        String queryCmd = null;
        String queryType = null;

        String regQueryCmd = "^\\" + Query.SEPARATOR + "([^" + Query.SEPARATOR + "]+)\\" + Query.SEPARATOR;

        Pattern p = Pattern.compile(regQueryCmd);
        Matcher m = p.matcher(content);
        while (m.find()) {
            queryCmd = m.group(1);
            queryType = QUERY_CMD.get(queryCmd);
        }

        // 将搜索字符及后面的+、空格、-等特殊符号去掉
        String keyWord = content.replaceAll("^" + Query.SEPARATOR + queryCmd + Query.SEPARATOR + Query.QUERY_LIST + Query.SEPARATOR + "[\\+ ~!@#%^-_=]?", "");

        TextMessage message = new TextMessage();

        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime());
        message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
        message.setContent("正在查询单位列表 " + queryType + ":" + keyWord);

        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);
        return respXml;
    }
}
