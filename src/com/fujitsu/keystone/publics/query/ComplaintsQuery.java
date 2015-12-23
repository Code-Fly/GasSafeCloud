package com.fujitsu.keystone.publics.query;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.GasHttpClientUtil;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.event.Event;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.CharEncoding;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Barrie on 15/12/22.
 */
public class ComplaintsQuery extends Query {
    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws JMSException, ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException, GasSafeException {
        super.execute(request, requestJson);

        String respXml = null;
        // 发送方帐号
        String fromUserName = requestJson.getString(Event.FROM_USER_NAME);
        // 开发者微信号
        String toUserName = requestJson.getString(Event.TO_USER_NAME);
        // 消息类型
        String msgType = requestJson.getString(Event.MSG_TYPE);

        TextMessage message = new TextMessage();

        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime());
        message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);

        Map<String, String> params = new HashMap<String, String>();
        params.put("ToUserName", requestJson.getString("ToUserName"));
        params.put("FromUserName", requestJson.getString("FromUserName"));
        params.put("CreateTime", requestJson.getString("CreateTime"));
        params.put("MsgType", requestJson.getString("MsgType"));
        params.put("MsgId", requestJson.getString("MsgId"));

        // 文本
        if (msgType.equals(MessageService.REQ_MESSAGE_TYPE_TEXT)) {
            params.put("Content", requestJson.getString("Content"));
            message.setContent(upload(params, fromUserName));
        }
        // 短视频
        else if (msgType.equals(MessageService.REQ_MESSAGE_TYPE_SHORT_VIDEO)) {
            params.put("MediaId", requestJson.getString("MediaId"));
            params.put("ThumbMediaId", requestJson.getString("ThumbMediaId"));
            message.setContent(upload(params, fromUserName));
        }
        // 视频
        else if (msgType.equals(MessageService.REQ_MESSAGE_TYPE_VIDEO)) {
            params.put("MediaId", requestJson.getString("MediaId"));
            params.put("ThumbMediaId", requestJson.getString("ThumbMediaId"));
            message.setContent(upload(params, fromUserName));
        }
        // 声音
        else if (msgType.equals(MessageService.REQ_MESSAGE_TYPE_VOICE)) {
            params.put("MediaId", requestJson.getString("MediaId"));
            params.put("Format", requestJson.getString("Format"));
            message.setContent(upload(params, fromUserName));
        }
        // 图像
        else if (msgType.equals(MessageService.REQ_MESSAGE_TYPE_IMAGE)) {
            params.put("MediaId", requestJson.getString("MediaId"));
            params.put("PicUrl", requestJson.getString("PicUrl"));
            message.setContent(upload(params, fromUserName));
        }

        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);

        return respXml;
    }

    private String upload(Map<String, String> params, String fromUserName) throws ConnectionFailedException, UnsupportedEncodingException {
        String uploadResp = GasHttpClientUtil.gasPost("ccstWeChatUPWCMessaging.htm", params, CharEncoding.UTF_8, fromUserName);
        if (null != uploadResp && !uploadResp.isEmpty()) {
            JSONObject jResp = JSONObject.fromObject(uploadResp);
            if (jResp.containsKey("errorCode")) {
                if (jResp.getString("errorCode").trim().equals("0")) {
                    return "您的投诉请求我们已经收到，请耐心等待处理结果。";
                } else {
                    return "系统请求socket出现异常:" + jResp.getString("errorCode");
                }
            } else {
                return "上传失败。";
            }
        } else {
            return "上传失败。";
        }
    }
}
