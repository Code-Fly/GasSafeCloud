/**
 *
 */
package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.GasHttpClientUtil;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.client.entity.WebSocketResFiled;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import com.fujitsu.keystone.publics.service.impl.UserService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.CharEncoding;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Barrie
 */
public class SubscribeEvent extends Event {

    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws AccessTokenException, WeChatException, ConnectionFailedException, JMSException, UnsupportedEncodingException, GasSafeException {
        super.execute(request, requestJson);

        String respXml = null;
        // 发送方帐号
        String fromUserName = requestJson.getString(FROM_USER_NAME);
        // 开发者微信号
        String toUserName = requestJson.getString(TO_USER_NAME);

        String at = KeystoneUtil.getAccessToken();
        UserService userService = new UserService();

        JSONObject user = userService.getWeChatUserInfo(fromUserName);

        Map<String, String> params = new HashMap<>();
        params.put("authorizeType", Const.gasApi.AUTHORIZETYPE);
        params.put("openId", fromUserName);
        params.put("nickName", user.getString("nickname"));
        params.put("sex", user.getString("sex"));
        params.put("language", user.getString("language"));
        params.put("country", user.getString("country"));
        params.put("province", user.getString("province"));
        params.put("city", user.getString("city"));
        params.put("headimgUrl", user.getString("headimgurl"));
        params.put("setCity", "null");
        params.put("sendLng", "null");
        params.put("sendLat", "null");
        params.put("cityName", "null");

        TextMessage message = new TextMessage();

        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime());
        message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);

        String gasResp = GasHttpClientUtil.doPost(Const.gasApi.URL + "ccstWeChatUpFansInfo.htm", params, CharEncoding.UTF_8);

        if (!GasHttpClientUtil.isValid(gasResp)) {
            StringBuffer sengMsg = new StringBuffer();

            sengMsg.append("系统请求socket出现异常:").append(JSONObject.fromObject(gasResp).get(WebSocketResFiled.ERROR_CODE));
            logger.info("setContent:" + sengMsg.toString());
            message.setContent(sengMsg.toString());
            // 将消息对象转换成xml
            respXml = MessageService.messageToXml(message);

            return respXml;
        }


        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("您好，欢迎关注")
                .append(Const.WECHART_NAME)
                .append(Const.LINE_SEPARATOR)
                .append(Const.WECHART_DOMAIN);
        message.setContent(stringBuffer.toString());

        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);

        return respXml;
    }

}
