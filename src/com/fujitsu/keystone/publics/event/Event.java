/**
 *
 */
package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author Barrie
 */
public abstract class Event {
    public static final String TO_USER_NAME = "ToUserName";
    public static final String FROM_USER_NAME = "FromUserName";
    public static final String CREATE_TIME = "CreateTime";
    public static final String MSG_TYPE = "MsgType";
    public static final String EVENT = "Event";
    // 事件类型：subscribe(订阅)
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    // 事件类型：unsubscribe(取消订阅)
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    // 事件类型：scan(用户已关注时的扫描带参数二维码)
    public static final String EVENT_TYPE_SCAN = "scan";
    // 事件类型：LOCATION(上报地理位置)
    public static final String EVENT_TYPE_LOCATION = "LOCATION";
    // 事件类型：CLICK(自定义菜单)
    public static final String EVENT_TYPE_CLICK = "CLICK";
    public static final String EVENT_MERCHANT_ORDER = "merchant_order";
    // 事件类型：kf_create_session(接入会话)
    public static final String EVENT_CUSTOMER_SERVICE_CREATE_SESSION = "kf_create_session";
    // 事件类型：kf_close_session(关闭会话)
    public static final String EVENT_CUSTOMER_SERVICE_CLOSE_SESSION = "kf_close_session";
    // 事件类型：scancode_waitmsg(扫码推事件且弹出“消息接收中”提示框的事件推送)
    public static final String EVENT_SCANCODE_WAIT_MSG = "scancode_waitmsg";
    // 事件类型：scancode_push(扫码推事件的事件推送)
    public static final String EVENT_SCANCODE_PUSH = "scancode_push";
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public String execute(HttpServletRequest request, JSONObject requestJson) throws JMSException, ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException, GasSafeException {

        return null;
    }
}
