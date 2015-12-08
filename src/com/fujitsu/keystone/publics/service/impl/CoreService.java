/**
 *
 */
package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.base.helper.WeChatClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.event.*;
import com.fujitsu.keystone.publics.query.CompanyDetailQuery;
import com.fujitsu.keystone.publics.query.CompanyListQuery;
import com.fujitsu.keystone.publics.query.DefaultQuery;
import com.fujitsu.keystone.publics.query.Query;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IMenuService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * @author Barrie
 */
@Service
public class CoreService extends BaseService implements ICoreService {
    @Resource
    IMenuService menuService;

    /**
     * 确认请求来自微信服务器
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (KeystoneUtil.checkSignature(Const.WECHART_TOKEN, signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }

    /**
     * 处理微信服务器发来的消息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 调用核心业务类接收消息、处理消息
        String respMessage = processRequest(request);

        if (null != respMessage) {
            // 响应消息
            PrintWriter out = response.getWriter();
            out.print(respMessage);
            out.close();
        }
    }

    /**
     * 获取access_token
     *
     * @param appid     凭证
     * @param appsecret 密钥
     * @return
     * @throws ConnectionFailedException
     */
    public JSONObject getAccessToken(String appid, String appsecret) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_GET_ACCESS_TOKEN.replace("APPID", appid).replace("APPSECRET", appsecret);

        String response = WeChatClientUtil.post(url, "UTF-8");

        return JSONObject.fromObject(response);
    }

    public JSONObject getJsapiTicket(String accessToken) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_JSAPI_TICKET.replace("ACCESS_TOKEN", accessToken);

        String response = WeChatClientUtil.post(url, "UTF-8");

        return JSONObject.fromObject(response);
    }

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return xml
     */
    private String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        try {
            // 调用parseXml方法解析请求消息
            JSONObject requestJson = MessageService.parseXml(request);
            // 消息类型
            String msgType = requestJson.getString(Event.MSG_TYPE);

            logger.info(requestJson.toString());
            // 事件推送
            if (msgType.equals(MessageService.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestJson.getString(Event.EVENT);
                // 订阅
                if (eventType.equals(Event.EVENT_TYPE_SUBSCRIBE)) {
                    Event event = new SubscribeEvent();
                    respXml = event.execute(request, requestJson);
                }
                // 取消订阅
                else if (eventType.equals(Event.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 暂不做处理
                }
                // 收到订单
                else if (eventType.equals(Event.EVENT_MERCHANT_ORDER)) {
                    Event event = new MerchantOrderEvent();
                    respXml = event.execute(request, requestJson);
                }
                // 开始客服会话
                else if (eventType.equals(Event.EVENT_CUSTOMER_SERVICE_CREATE_SESSION)) {
                    Event event = new CustomerServiceCreateSessionEvent();
                    respXml = event.execute(request, requestJson);
                }
                // 关闭客服会话
                else if (eventType.equals(Event.EVENT_CUSTOMER_SERVICE_CLOSE_SESSION)) {
                    Event event = new CustomerServiceCloseSessionEvent();
                    respXml = event.execute(request, requestJson);
                }
                // 扫码推事件且弹出“消息接收中”提示框的事件推送
                else if (eventType.equals(Event.EVENT_SCANCODE_WAIT_MSG)) {
                    Event event = new ScancodeWaitmsgEvent();
                    respXml = event.execute(request, requestJson);
                }
                // 扫码推事件的事件推送
                else if (eventType.equals(Event.EVENT_SCANCODE_PUSH)) {
                    Event event = new ScancodePushEvent();
                    respXml = event.execute(request, requestJson);
                }
                // 自定义菜单点击事件
                else if (eventType.equals(Event.EVENT_TYPE_CLICK)) {
                    Event event = new ClickEvent();
                    respXml = event.execute(request, requestJson);
                }
                // 上报地理位置
                else if (eventType.equals(Event.EVENT_TYPE_LOCATION)) {
                    Event event = new LocationEvent();
                    respXml = event.execute(request, requestJson);
                }
            }
            // 当用户发文本消息时
            else if (msgType.equals(MessageService.REQ_MESSAGE_TYPE_TEXT)) {
                // 文本消息内容
                String content = requestJson.getString("Content").trim().toUpperCase();

                // 企业列表正则
                String regCorpList = "^" + Query.SEPARATOR + "[^" + Query.SEPARATOR + "]+" + Query.SEPARATOR + Query.QUERY_LIST + Query.SEPARATOR + "[^" + Query.SEPARATOR + "]+$";
                // 企业详情正则
                String regCorpDetail = "^" + Query.SEPARATOR + "[^" + Query.SEPARATOR + "]+" + Query.SEPARATOR + Query.QUERY_DETAIL + Query.SEPARATOR + "[^" + Query.SEPARATOR + "]+$";
                // 客服消息正则
                String regCustomerService = "^" + Query.SEPARATOR + Query.CUSTOMER_SERVICE + "$";

                // 查询企业列表
                if (Pattern.compile(regCorpList).matcher(content).matches()) {
                    Query query = new CompanyListQuery();
                    respXml = query.execute(request, requestJson);
                }
                // 查询企业详情
                else if (Pattern.compile(regCorpDetail).matcher(content).matches()) {
                    Query query = new CompanyDetailQuery();
                    respXml = query.execute(request, requestJson);
                }
                //客服消息
                else if (Pattern.compile(regCustomerService).matcher(content).matches()) {
                    Event event = new CustomerServiceTransferEvent();
                    respXml = event.execute(request, requestJson);
                }
                //其它
                else {
                    Query query = new DefaultQuery();
                    respXml = query.execute(request, requestJson);
                }
            }
            // 默认事件响应
            else {
                Event event = new DefaultEvent();
                respXml = event.execute(request, requestJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }

}
