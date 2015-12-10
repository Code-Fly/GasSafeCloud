/**
 * 
 */
package com.fujitsu.keystone.publics.query;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.CharEncoding;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.GasHttpClientUtil;
import com.fujitsu.client.entity.BarcodegetBottlePsafeResMsg;
import com.fujitsu.client.entity.BarcodegetBottlePsafeResult;
import com.fujitsu.client.entity.SocketFailCode;
import com.fujitsu.client.entity.WebSocketResFiled;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.event.Event;
import com.fujitsu.keystone.publics.service.impl.MessageService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author Administrator
 *
 */
public class AqdwQuery  extends Query {
	@Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws JMSException, WeChatException, ConnectionFailedException, AccessTokenException, UnsupportedEncodingException, GasSafeException {
      
		super.execute(request, requestJson);
        String respXml = null;
        // 发送方帐号
        String fromUserName = requestJson.getString(Event.FROM_USER_NAME);
        // 开发者微信号
        String toUserName = requestJson.getString(Event.TO_USER_NAME);

        // "#AQ#013264+2015+MS"
        String content = requestJson.getString("Content").trim().toUpperCase();
        content = content.substring(4);
        logger.info("AqdwQuery content="+content);
        String[] messArray = content.split("[+]");
        StringBuffer  sengMsg = new StringBuffer(); 
        TextMessage message = new TextMessage();

        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime());
        message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
        if (messArray.length == 3) {
        	  Map<String, String> params_AQDW = new HashMap<String, String>();
              params_AQDW.put("pid", messArray[0]);
              params_AQDW.put("pYear", messArray[1]);
              params_AQDW.put("pCode", messArray[2]);
              String response = GasHttpClientUtil.gasPost("ccstWeChatBarcodegetBottlePsafe.htm", params_AQDW,
                      CharEncoding.UTF_8, fromUserName);
              BarcodegetBottlePsafeResMsg messageObject = new BarcodegetBottlePsafeResMsg();
              JSONObject object = JSONObject.fromObject(response);
              if (SocketFailCode.ERR_CODE_LENGTH == response.length()) {
                  sengMsg.append("系统请求socket出现异常:").append(response);
              } else {
                  if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
                      sengMsg.append("系统请求socket出现异常:").append(object.get(WebSocketResFiled.ERROR_CODE));
                  } else {
                      JsonConfig jsonConfig = new JsonConfig();
                      jsonConfig.setRootClass(BarcodegetBottlePsafeResMsg.class);
                      Map<String, Class> classMap = new HashMap<String, Class>();
                      classMap.put("result", BarcodegetBottlePsafeResult.class);
                      jsonConfig.setClassMap(classMap);
                      messageObject = (BarcodegetBottlePsafeResMsg) JSONObject.toBean(object, jsonConfig);
                      sengMsg.append("气瓶制造单位代号:").append(messageObject.getResult().get(0).getpCode())
                              .append(Const.LINE_SEPARATOR).append("气瓶使用证编号 :")
                              .append(messageObject.getResult().get(0).getSyzbh()).append(Const.LINE_SEPARATOR)
                              .append("气瓶注册代码:").append(messageObject.getResult().get(0).getZcdm())
                              .append(Const.LINE_SEPARATOR).append("气瓶结构:")
                              .append(messageObject.getResult().get(0).getQpStructureName()).append(Const.LINE_SEPARATOR)
                              .append("气瓶品种:").append(messageObject.getResult().get(0).getClassName())
                              .append(Const.LINE_SEPARATOR).append("气瓶型号:")
                              .append(messageObject.getResult().get(0).getTypeName()).append(Const.LINE_SEPARATOR)
                              .append("气瓶编号:").append(messageObject.getResult().get(0).getPid()).append(Const.LINE_SEPARATOR)
                              .append("充装介质:").append(messageObject.getResult().get(0).getMediumName())
                              .append(Const.LINE_SEPARATOR).append("出厂日期:")
                              .append(messageObject.getResult().get(0).getpDate()).append(Const.LINE_SEPARATOR)
                              .append("上检日期:").append(messageObject.getResult().get(0).getfDate())
                              .append(Const.LINE_SEPARATOR).append("下检日期:").append(messageObject.getResult().get(0).getXjrq())
                              .append(Const.LINE_SEPARATOR).append("报废日期:").append(messageObject.getResult().get(0).getBfrq())
                              .append(Const.LINE_SEPARATOR).append(" 用户:")
                              .append(messageObject.getResult().get(0).getUserName()).append(Const.LINE_SEPARATOR)
                              .append("用户位置:").append(messageObject.getResult().get(0).getUserInfo())
                              .append(Const.LINE_SEPARATOR).append("用户联系方式:")
                              .append(messageObject.getResult().get(0).getPhone());
                  }
              } 
		} else {
			sengMsg.append("输入有误！请输入#AQ#气瓶编号+生产年度+制造单位代号");
        }
        logger.info("sengMsg = " + sengMsg.toString());
        message.setContent(sengMsg.toString());
        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);
        
        return respXml;
        
	}
}
