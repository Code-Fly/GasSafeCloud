package com.fujitsu.keystone.publics.query;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.GasHttpClientUtil;
import com.fujitsu.client.entity.CompanyListResMsg;
import com.fujitsu.client.entity.CompanyListResult;
import com.fujitsu.client.entity.SocketFailCode;
import com.fujitsu.client.entity.WebSocketResFiled;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.event.Event;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.CharEncoding;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Barrie on 15/11/26.
 */
public class CompanyListQuery extends Query {
    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws JMSException, WeChatException, ConnectionFailedException, AccessTokenException, UnsupportedEncodingException, GasSafeException {
        super.execute(request, requestJson);

        String respXml = null;
        // 发送方帐号
        String fromUserName = requestJson.getString(Event.FROM_USER_NAME);
        // 开发者微信号
        String toUserName = requestJson.getString(Event.TO_USER_NAME);

        String content = requestJson.getString("Content").trim().toUpperCase();

        String queryCmd = null;
        String queryType = null;

        String[] cmds = content.split(Query.SEPARATOR_3);
        queryCmd = cmds[1];
        queryType = QUERY_CMD_TYPE.get(queryCmd);

        TextMessage message = new TextMessage();

        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime());
        message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);

        if (null != queryType) {
            StringBuffer buffer = new StringBuffer();
            // 将搜索字符及后面的+、空格、-等特殊符号去掉
            String keyWord = content.replaceAll("^" + Query.SEPARATOR_3 + queryCmd + Query.SEPARATOR_3 + "[\\+ ~!@#%^-_=]?", "");

            Map<String, String> params = new HashMap<String, String>();
            params.put("uName", keyWord);
            params.put("qyType", queryType);

            String response = GasHttpClientUtil.gasPost("ccstWeChatGetDWList.htm", params, CharEncoding.UTF_8, fromUserName);
            if (SocketFailCode.ERR_CODE_LENGTH == response.length()) {
                buffer.append("系统请求socket出现异常:").append(response);
            } else {
                CompanyListResMsg retMsg = new CompanyListResMsg();
                JSONObject object = JSONObject.fromObject(response);

                if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
                    buffer.append("系统请求socket出现异常:").append(object.get(WebSocketResFiled.ERROR_CODE));
                } else {
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setRootClass(CompanyListResMsg.class);
                    Map<String, Class> classMap = new HashMap<String, Class>();
                    classMap.put("result", CompanyListResult.class);
                    jsonConfig.setClassMap(classMap);
                    retMsg = (CompanyListResMsg) JSONObject.toBean(object, jsonConfig);

                    buffer.append("单位列表:").append(Const.LINE_SEPARATOR);
                    buffer.append(Const.LINE_SEPARATOR);
                    for (int i = 0; i < retMsg.getResult().size(); i++) {
                        buffer.append(i + 1 + "." + retMsg.getResult().get(i).getUnitName()).append(Const.LINE_SEPARATOR);
                    }
                }
            }
            message.setContent(buffer.toString());
        } else {
            message.setContent("输入有误! ");
        }

        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);

        return respXml;
    }
}

