package com.fujitsu.keystone.publics.query;

import com.fujitsu.base.client.GasWebSocketClient;
import com.fujitsu.base.client.QueryCompanyDetailClient;
import com.fujitsu.base.client.QueryCompanyDetailConnect;
import com.fujitsu.base.client.entity.CompanyDetailResMsg;
import com.fujitsu.base.client.entity.SocketFailCode;
import com.fujitsu.base.helper.GasWebSocketUtil;
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
public class CompanyDetailQuery extends Query {
    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) {
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
            String keyWord = content.replaceAll("^" + Query.SEPARATOR + queryCmd + Query.SEPARATOR + Query.QUERY_DETAIL + Query.SEPARATOR + "[\\+ ~!@#%^-_=]?", "");

            StringBuffer socketParams = new StringBuffer();
            socketParams.append("uName=").append(keyWord);
            socketParams.append("&token=").append(GasWebSocketClient.SOCKET_TOKEN);
            socketParams.append("&qyType=").append(queryType);
            CompanyDetailResMsg retMsg = getCompanyDetailResMsg(socketParams.toString(), 0);
            if (0 == retMsg.getErrorCode()) {
                // 充装存储
                if (Query.FILLING_STORAGE.equals(queryCmd)) {
                    buffer.append("单位信息:").append(ENTER);
                    for (int i = 0; i < retMsg.getResult().size(); i++) {
                        logger.info(retMsg.getResult().get(i).toString());
                        buffer.append("授权编号:" + retMsg.getResult().get(i).getRnoId()).append(ENTER);
                        buffer.append("单位名称:" + retMsg.getResult().get(i).getUnitName()).append(ENTER);
                        buffer.append("企业社会信用:" + retMsg.getResult().get(i).getQybh()).append(ENTER);
                        buffer.append("充装地址:" + retMsg.getResult().get(i).getFillAddress()).append(ENTER);
                        buffer.append("行政区编码:" + retMsg.getResult().get(i).getAreaCode()).append(ENTER);
                        buffer.append("气瓶充装证编号:" + retMsg.getResult().get(i).getQpczLicbh()).append(ENTER);
                        buffer.append("许可证有效期开始:" + retMsg.getResult().get(i).getLicStart()).append(ENTER);
                        buffer.append("许可证有效终止:" + retMsg.getResult().get(i).getLicEnd()).append(ENTER);
                        buffer.append("充装范围代号:" + retMsg.getResult().get(i).getFillType()).append(ENTER);
                        buffer.append("气瓶结构:" + retMsg.getResult().get(i).getQpStructure()).append(ENTER);
                        buffer.append("充装气体类别:" + retMsg.getResult().get(i).getFillinggasType()).append(ENTER);
                        buffer.append("发证机关:" + retMsg.getResult().get(i).getFazhengjigou()).append(ENTER);
                        buffer.append(ENTER);
                    }
                }
                // 配送运输
                else if (Query.DISTRIBUTION_TRANSPORTATION.equals(queryCmd)) {
                    buffer.append("单位信息:").append(ENTER);
                    for (int i = 0; i < retMsg.getResult().size(); i++) {
                        logger.info(retMsg.getResult().get(i).toString());
                        buffer.append("授权编号:" + retMsg.getResult().get(i).getRnoId()).append(ENTER);
                        buffer.append("单位名称:" + retMsg.getResult().get(i).getUnitName()).append(ENTER);
                        buffer.append("企业社会信用:" + retMsg.getResult().get(i).getOrganizationCode()).append(ENTER);
                        buffer.append("单位地址:" + retMsg.getResult().get(i).getUnitAddress()).append(ENTER);
                        buffer.append("行政区编码:" + retMsg.getResult().get(i).getAreaCode()).append(ENTER);
                        buffer.append("燃气许可证编号:" + retMsg.getResult().get(i).getLicenceId()).append(ENTER);
                        buffer.append("许可证有效期开始:" + retMsg.getResult().get(i).getLicStart()).append(ENTER);
                        buffer.append("许可证有效终止:" + retMsg.getResult().get(i).getLicEnd()).append(ENTER);
                        buffer.append("配送经营种类:" + retMsg.getResult().get(i).getPszl()).append(ENTER);
                        buffer.append("发证机关:" + retMsg.getResult().get(i).getFazhengjigou()).append(ENTER);
                        buffer.append(ENTER);
                    }
                }
                // 检验监测
                else if (Query.INSPECTION_TESTING.equals(queryCmd)) {
                    buffer.append("单位信息:").append(ENTER);
                    for (int i = 0; i < retMsg.getResult().size(); i++) {
                        logger.info(retMsg.getResult().get(i).toString());
                        buffer.append("授权编号:" + retMsg.getResult().get(i).getRnoId()).append(ENTER);
                        buffer.append("单位名称:" + retMsg.getResult().get(i).getUnitName()).append(ENTER);
                        buffer.append("企业社会信用:" + retMsg.getResult().get(i).getOrganizationCode()).append(ENTER);
                        buffer.append("单位地址:" + retMsg.getResult().get(i).getUnitAddress()).append(ENTER);
                        buffer.append("行政区编码:" + retMsg.getResult().get(i).getAreaCode()).append(ENTER);
                        buffer.append("检验许可证编号:" + retMsg.getResult().get(i).getBusinessCode()).append(ENTER);
                        buffer.append("许可证有效期开始:" + retMsg.getResult().get(i).getLicStart()).append(ENTER);
                        buffer.append("许可证有效终止:" + retMsg.getResult().get(i).getLicEnd()).append(ENTER);
                        buffer.append("检验项目代码:" + retMsg.getResult().get(i).getClassID()).append(ENTER);
                        buffer.append("核准项目:" + retMsg.getResult().get(i).getLicProject()).append(ENTER);
                        buffer.append("发证机关:" + retMsg.getResult().get(i).getFazhengjigou()).append(ENTER);
                        buffer.append(ENTER);
                    }
                }

            } else {
                buffer.append("系统请求socket出现异常:").append(retMsg.getErrorCode()).append(ENTER);
            }
            message.setContent(buffer.toString());

        } else {
            message.setContent("输入有误! ");
        }

        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);
        return respXml;
    }

    /**
     * @param socketParams
     * @param times
     * @return
     */
    private CompanyDetailResMsg getCompanyDetailResMsg(String socketParams, int times) {
        logger.info("getCompanyDetailResMsg times=" + times);
        QueryCompanyDetailConnect.sendMsg(socketParams.toString());
        CompanyDetailResMsg messageObject = QueryCompanyDetailClient.messageObject;
        if ((times) < 1 && (SocketFailCode.CODE_100001 == messageObject.getErrorCode()
                || SocketFailCode.CODE_100002 == messageObject.getErrorCode())) {
            logger.info("Bottle times=" + times);
            GasWebSocketUtil.accessWSToken();
            getCompanyDetailResMsg(socketParams, times + 1);
        }
        logger.info("getCompanyDetailResMsg messageObject=" + messageObject.getMessage());
        return messageObject;
    }
}
