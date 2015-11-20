/**
 * 
 */
package com.fujitsu.keystone.publics.service.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.event.ClickEvent;
import com.fujitsu.keystone.publics.event.CustomerServiceCloseSessionEvent;
import com.fujitsu.keystone.publics.event.CustomerServiceCreateSessionEvent;
import com.fujitsu.keystone.publics.event.CustomerServiceTransferEvent;
import com.fujitsu.keystone.publics.event.Event;
import com.fujitsu.keystone.publics.event.MerchantOrderEvent;
import com.fujitsu.keystone.publics.event.ScancodePushEvent;
import com.fujitsu.keystone.publics.event.ScancodeWaitmsgEvent;
import com.fujitsu.keystone.publics.event.SubscribeEvent;
import com.fujitsu.keystone.publics.query.DistributionTransportationQuery;
import com.fujitsu.keystone.publics.query.FillingStorageQuery;
import com.fujitsu.keystone.publics.query.InspectionTestingQuery;
import com.fujitsu.keystone.publics.query.Query;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IMenuService;

/**
 * @author Barrie
 *
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
		if (KeystoneUtil.checkSignature(TOKEN, signature, timestamp, nonce)) {
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

		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 * @throws ConnectionFailedException
	 */
	public JSONObject getAccessToken(String appid, String appsecret) throws ConnectionFailedException {
		// WeChatAccessToken accessToken = null;

		String url = URL_GET_ACCESS_TOKEN.replace("APPID", appid).replace("APPSECRET", appsecret);

		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", null);

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
	}

	public JSONObject getJsapiTicket(String accessToken) throws ConnectionFailedException {
		// WeChatAccessToken accessToken = null;

		String url = URL_JSAPI_TICKET.replace("ACCESS_TOKEN", accessToken);

		JSONObject response = HttpClientUtil.doHttpsRequest(url, "GET", null);

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
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
					// 收到订单
				} else if (eventType.equals(Event.EVENT_MERCHANT_ORDER)) {
					Event event = new MerchantOrderEvent();
					respXml = event.execute(request, requestJson);
					// 开始客服会话
				} else if (eventType.equals(Event.EVENT_CUSTOMER_SERVICE_CREATE_SESSION)) {
					Event event = new CustomerServiceCreateSessionEvent();
					respXml = event.execute(request, requestJson);
					// 关闭客服会话
				} else if (eventType.equals(Event.EVENT_CUSTOMER_SERVICE_CLOSE_SESSION)) {
					Event event = new CustomerServiceCloseSessionEvent();
					respXml = event.execute(request, requestJson);
					// 扫码推事件且弹出“消息接收中”提示框的事件推送
				} else if (eventType.equals(Event.EVENT_SCANCODE_WAIT_MSG)) {
					Event event = new ScancodeWaitmsgEvent();
					respXml = event.execute(request, requestJson);
					// 扫码推事件的事件推送
				} else if (eventType.equals(Event.EVENT_SCANCODE_PUSH)) {
					Event event = new ScancodePushEvent();
					respXml = event.execute(request, requestJson);
				}
				// 自定义菜单点击事件
				else if (eventType.equals(Event.EVENT_TYPE_CLICK)) {
					Event event = new ClickEvent();
					respXml = event.execute(request, requestJson);
				}
			}
			// 当用户发文本消息时
			else if (msgType.equals(MessageService.REQ_MESSAGE_TYPE_TEXT)) {
				// 文本消息内容
				String content = requestJson.getString("Content").trim();
				// 查询配送运输
				if (content.startsWith(Query.SEPARATOR + Query.DISTRIBUTION_TRANSPORTATION + Query.SEPARATOR)) {
					Query query = new DistributionTransportationQuery();
					respXml = query.execute(request, requestJson);
					// 查询充装存储
				} else if (content.startsWith(Query.SEPARATOR + Query.FILLING_STORAGE + Query.SEPARATOR)) {
					Query query = new FillingStorageQuery();
					respXml = query.execute(request, requestJson);
					// 查询检验检测
				} else if (content.startsWith(Query.SEPARATOR + Query.INSPECTION_TESTING + Query.SEPARATOR)) {
					Query query = new InspectionTestingQuery();
					respXml = query.execute(request, requestJson);
					// 客服转接
				} else if (content.startsWith(Query.SEPARATOR + Query.CUSTOMER_SERVICE + Query.SEPARATOR)) {
					Event event = new CustomerServiceTransferEvent();
					respXml = event.execute(request, requestJson);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}

}
