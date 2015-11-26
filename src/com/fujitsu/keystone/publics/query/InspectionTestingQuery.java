/**
 *
 */
package com.fujitsu.keystone.publics.query;

import com.fujitsu.base.client.GasBarcodegetBottleClient;
import com.fujitsu.base.client.GasBarcodegetBottleConnect;
import com.fujitsu.base.client.entity.BarcodegetBottleResMsg;
import com.fujitsu.base.client.entity.SocketFailCode;
import com.fujitsu.base.helper.GasWebSocketUtil;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.event.Event;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Barrie
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

        String queryType = Query.SEPARATOR + Query.INSPECTION_TESTING + Query.SEPARATOR;
        String content = requestJson.getString("Content").trim();
        // 将搜索字符及后面的+、空格、-等特殊符号去掉
        String keyWord = content.replaceAll("^" + queryType + "[\\+ ~!@#%^-_=]?", "");

        TextMessage message = new TextMessage();

        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime());
        message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
        message.setContent("正在查询 " + INSPECTION_TESTING + ":" + keyWord);

        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);
        return respXml;
    }

    private BarcodegetBottleResMsg getBarResMsg(String socketParams, int times) {
        logger.info("getBarResMsg times=" + times);
        Long systemTime = System.currentTimeMillis();
        logger.info("system time :=" + systemTime);
        GasBarcodegetBottleConnect.sendMsg(socketParams.toString());
        logger.info("system time :=" + (System.currentTimeMillis() - systemTime));
        BarcodegetBottleResMsg messageObject = GasBarcodegetBottleClient.messageObject;
        if ((times) < 1 && (SocketFailCode.CODE_100001 == messageObject.getErrorCode()
                || SocketFailCode.CODE_100002 == messageObject.getErrorCode())) {
            logger.info("Bar times=" + times);
            GasWebSocketUtil.accessWSToken();
            getBarResMsg(socketParams, 1);
        }
        logger.info("getBarResMsg messageObject=" + messageObject.getMessage());
        return messageObject;
    }
}
