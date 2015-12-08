package com.fujitsu.keystone.publics.query;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.client.GasWebSocketClient;
import com.fujitsu.client.QueryCompanyListClient;
import com.fujitsu.client.QueryCompanyListConnect;
import com.fujitsu.client.entity.CompanyListResMsg;
import com.fujitsu.client.entity.SocketFailCode;
import com.fujitsu.base.constants.Const;
import com.fujitsu.base.helper.GasWebSocketUtil;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.event.Event;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import net.sf.json.JSONObject;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Barrie on 15/11/26.
 */
public class CompanyListQuery extends Query {
    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws JMSException, WeChatException, ConnectionFailedException, AccessTokenException, UnsupportedEncodingException, GasSafeException {
        String respXml = null;
        // 发送方帐号
        String fromUserName = requestJson.getString(Event.FROM_USER_NAME);
        // 开发者微信号
        String toUserName = requestJson.getString(Event.TO_USER_NAME);

        String content = requestJson.getString("Content").trim().toUpperCase();

        String queryCmd = null;
        String queryType = null;

        String regQueryCmd = "^\\" + Query.SEPARATOR + "([^" + Query.SEPARATOR + "]+)\\" + Query.SEPARATOR;

        Pattern p = Pattern.compile(regQueryCmd);
        Matcher m = p.matcher(content);
        while (m.find()) {
            queryCmd = m.group(1);
            queryType = QUERY_CMD_TYPE.get(queryCmd);
        }

        TextMessage message = new TextMessage();

        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime());
        message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);

        if (null != queryType) {
            StringBuffer buffer = new StringBuffer();
            // 将搜索字符及后面的+、空格、-等特殊符号去掉
            String keyWord = content.replaceAll("^" + Query.SEPARATOR + queryCmd + Query.SEPARATOR + Query.QUERY_LIST + Query.SEPARATOR + "[\\+ ~!@#%^-_=]?", "");

            StringBuffer socketParams = new StringBuffer();
            socketParams.append("uName=").append(keyWord);
            socketParams.append("&token=").append(GasWebSocketClient.SOCKET_TOKEN);
            socketParams.append("&qyType=").append(queryType);
            CompanyListResMsg retMsg = getCompanyListResMsg(socketParams.toString(), 0);
            if (0 == retMsg.getErrorCode()) {
                buffer.append("单位列表:").append(Const.LINE_SEPARATOR);
                buffer.append(Const.LINE_SEPARATOR);
                for (int i = 0; i < retMsg.getResult().size(); i++) {
                    buffer.append(i + 1 + "." + retMsg.getResult().get(i).getUnitName()).append(Const.LINE_SEPARATOR);
                }
            } else {
                buffer.append("系统请求socket出现异常:").append(retMsg.getErrorCode()).append(Const.LINE_SEPARATOR);
            }
            message.setContent(buffer.toString());
        } else {
            message.setContent("输入有误! ");
        }

        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);

        super.execute(request, requestJson);
        return respXml;
    }

    /**
     *
     * @param socketParams
     * @param times
     * @return
     */
    private CompanyListResMsg getCompanyListResMsg(String socketParams, int times) {
        logger.info("getCompanyListResMsg times=" + times);
        QueryCompanyListConnect.sendMsg(socketParams.toString());
        CompanyListResMsg messageObject = QueryCompanyListClient.messageObject;
        if ((times) < 1 && (SocketFailCode.CODE_100001 == messageObject.getErrorCode()
                || SocketFailCode.CODE_100002 == messageObject.getErrorCode())) {
            logger.info("Bottle times=" + times);
            GasWebSocketUtil.accessWSToken();
            getCompanyListResMsg(socketParams, times + 1);
        }
        logger.info("getCompanyListResMsg messageObject=" + messageObject.getMessage());
        return messageObject;
    }
}

