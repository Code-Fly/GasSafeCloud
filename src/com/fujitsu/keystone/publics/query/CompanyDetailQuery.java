package com.fujitsu.keystone.publics.query;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.GasHttpClientUtil;
import com.fujitsu.client.entity.CompanyDetailResMsg;
import com.fujitsu.client.entity.CompanyDetailResult;
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
public class CompanyDetailQuery extends Query {
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

        queryCmd = content.split(Query.SEPARATOR_3)[1];
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

            String response = GasHttpClientUtil.gasPost("ccstWeChatGetDWInfo.htm", params, CharEncoding.UTF_8, fromUserName);
            if (SocketFailCode.ERR_CODE_LENGTH == response.length()) {
                buffer.append("系统请求socket出现异常:").append(response);
            } else {
                CompanyDetailResMsg retMsg = new CompanyDetailResMsg();
                JSONObject object = JSONObject.fromObject(response);

                if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
                    buffer.append("系统请求socket出现异常:").append(object.get(WebSocketResFiled.ERROR_CODE));
                } else {
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setRootClass(CompanyDetailResMsg.class);
                    Map<String, Class> classMap = new HashMap<String, Class>();
                    classMap.put("result", CompanyDetailResult.class);
                    jsonConfig.setClassMap(classMap);
                    retMsg = (CompanyDetailResMsg) JSONObject.toBean(object, jsonConfig);

                    // 充装存储
                    if (Query.FILLING_STORAGE_DETAIL.equals(queryCmd)) {
                        buffer.append("单位信息:").append(Const.LINE_SEPARATOR);
                        buffer.append(Const.LINE_SEPARATOR);
                        for (int i = 0; i < retMsg.getResult().size(); i++) {
                            logger.info(retMsg.getResult().get(i).toString());
                            buffer.append("授权编号:" + retMsg.getResult().get(i).getRnoId()).append(Const.LINE_SEPARATOR);
                            buffer.append("单位名称:" + retMsg.getResult().get(i).getUnitName()).append(Const.LINE_SEPARATOR);
                            buffer.append("企业社会信用:" + retMsg.getResult().get(i).getQybh()).append(Const.LINE_SEPARATOR);
                            buffer.append("充装地址:" + retMsg.getResult().get(i).getFillAddress()).append(Const.LINE_SEPARATOR);
                            buffer.append("行政区编码:" + retMsg.getResult().get(i).getAreaCode()).append(Const.LINE_SEPARATOR);
                            buffer.append("气瓶充装证编号:" + retMsg.getResult().get(i).getQpczLicbh()).append(Const.LINE_SEPARATOR);
                            buffer.append("许可证有效期开始:" + retMsg.getResult().get(i).getLicStart()).append(Const.LINE_SEPARATOR);
                            buffer.append("许可证有效终止:" + retMsg.getResult().get(i).getLicEnd()).append(Const.LINE_SEPARATOR);
                            buffer.append("充装范围代号:" + retMsg.getResult().get(i).getFillType()).append(Const.LINE_SEPARATOR);
                            buffer.append("气瓶结构:" + retMsg.getResult().get(i).getQpStructure()).append(Const.LINE_SEPARATOR);
                            buffer.append("充装气体类别:" + retMsg.getResult().get(i).getFillinggasType()).append(Const.LINE_SEPARATOR);
                            buffer.append("发证机关:" + retMsg.getResult().get(i).getFazhengjigou()).append(Const.LINE_SEPARATOR);
                            buffer.append(Const.LINE_SEPARATOR);
                        }
                    }
                    // 配送运输
                    else if (Query.DISTRIBUTION_TRANSPORTATION_DETAIL.equals(queryCmd)) {
                        buffer.append("单位信息:").append(Const.LINE_SEPARATOR);
                        buffer.append(Const.LINE_SEPARATOR);
                        for (int i = 0; i < retMsg.getResult().size(); i++) {
                            logger.info(retMsg.getResult().get(i).toString());
                            buffer.append("授权编号:" + retMsg.getResult().get(i).getRnoId()).append(Const.LINE_SEPARATOR);
                            buffer.append("单位名称:" + retMsg.getResult().get(i).getUnitName()).append(Const.LINE_SEPARATOR);
                            buffer.append("企业社会信用:" + retMsg.getResult().get(i).getOrganizationCode()).append(Const.LINE_SEPARATOR);
                            buffer.append("单位地址:" + retMsg.getResult().get(i).getUnitAddress()).append(Const.LINE_SEPARATOR);
                            buffer.append("行政区编码:" + retMsg.getResult().get(i).getAreaCode()).append(Const.LINE_SEPARATOR);
                            buffer.append("燃气许可证编号:" + retMsg.getResult().get(i).getLicenceId()).append(Const.LINE_SEPARATOR);
                            buffer.append("许可证有效期开始:" + retMsg.getResult().get(i).getLicStart()).append(Const.LINE_SEPARATOR);
                            buffer.append("许可证有效终止:" + retMsg.getResult().get(i).getLicEnd()).append(Const.LINE_SEPARATOR);
                            buffer.append("配送经营种类:" + retMsg.getResult().get(i).getPszl()).append(Const.LINE_SEPARATOR);
                            buffer.append("发证机关:" + retMsg.getResult().get(i).getFazhengjigou()).append(Const.LINE_SEPARATOR);
                            buffer.append(Const.LINE_SEPARATOR);
                        }
                    }
                    // 检验监测
                    else if (Query.INSPECTION_TESTING_DETAIL.equals(queryCmd)) {
                        buffer.append("单位信息:").append(Const.LINE_SEPARATOR);
                        buffer.append(Const.LINE_SEPARATOR);
                        for (int i = 0; i < retMsg.getResult().size(); i++) {
                            logger.info(retMsg.getResult().get(i).toString());
                            buffer.append("授权编号:" + retMsg.getResult().get(i).getRnoId()).append(Const.LINE_SEPARATOR);
                            buffer.append("单位名称:" + retMsg.getResult().get(i).getUnitName()).append(Const.LINE_SEPARATOR);
                            buffer.append("企业社会信用:" + retMsg.getResult().get(i).getOrganizationCode()).append(Const.LINE_SEPARATOR);
                            buffer.append("单位地址:" + retMsg.getResult().get(i).getUnitAddress()).append(Const.LINE_SEPARATOR);
                            buffer.append("行政区编码:" + retMsg.getResult().get(i).getAreaCode()).append(Const.LINE_SEPARATOR);
                            buffer.append("检验许可证编号:" + retMsg.getResult().get(i).getBusinessCode()).append(Const.LINE_SEPARATOR);
                            buffer.append("许可证有效期开始:" + retMsg.getResult().get(i).getLicStart()).append(Const.LINE_SEPARATOR);
                            buffer.append("许可证有效终止:" + retMsg.getResult().get(i).getLicEnd()).append(Const.LINE_SEPARATOR);
                            buffer.append("检验项目代码:" + retMsg.getResult().get(i).getClassID()).append(Const.LINE_SEPARATOR);
                            buffer.append("核准项目:" + retMsg.getResult().get(i).getLicProject()).append(Const.LINE_SEPARATOR);
                            buffer.append("发证机关:" + retMsg.getResult().get(i).getFazhengjigou()).append(Const.LINE_SEPARATOR);
                            buffer.append(Const.LINE_SEPARATOR);
                        }
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
