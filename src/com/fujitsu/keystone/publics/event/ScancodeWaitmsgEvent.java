package com.fujitsu.keystone.publics.event;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fujitsu.base.client.BarcodegetBottleResMsg;
import com.fujitsu.base.client.GasBarcodegetBottleClient;
import com.fujitsu.base.client.GasBarcodegetBottleConnect;
import com.fujitsu.base.client.GasWebSocketClient;
import com.fujitsu.base.client.SocketFailCode;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.GasWebSocketUtil;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MenuService;
import com.fujitsu.keystone.publics.service.impl.MessageService;

public class ScancodeWaitmsgEvent extends Event {

	public static String SCAN_RESULT = "ScanResult";

	public static String SCAN_CODE_INFO = "ScanCodeInfo";

	public static String EVENT_KEY = "EventKey";

	/**
	 * @throws AccessTokenException
	 * @throws ConnectionFailedException
	 * 
	 */
	public String execute(HttpServletRequest request, JSONObject requestJson) throws ConnectionFailedException, AccessTokenException {
		String respXml = null;

		String fromUserName = requestJson.getString(FROM_USER_NAME);
		String toUserName = requestJson.getString(TO_USER_NAME);
		String eventKey = requestJson.getString(EVENT_KEY);
		JSONObject scanCodeInfo = JSONObject.fromObject(requestJson.get(SCAN_CODE_INFO));
		String scanResult = scanCodeInfo.getString(SCAN_RESULT);

		TextMessage message = new TextMessage();
		message.setToUserName(fromUserName);
		message.setFromUserName(toUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
		/**
		 * 处理message 推送给用户的message
		 * [QP02001,132020000001,AG,323232,2015年09月,2045年09月]气瓶安全云www.qpsafe.cn
		 */
		int lastIndex = scanResult.lastIndexOf("]");
		 //QP02001,132020000001,AG,323232,2015年09月,2045年09月
		String tmp = scanResult.substring(1, lastIndex);
		// [QP02001,132020000001,AG,323232,2015年09月,2045年09月]
		String[] messArray = tmp.split(",");
		StringBuffer sengMsg = new StringBuffer();
		// 身份查询
		if (MenuService.QP_SFCX.equals(eventKey)) {
		     StringBuffer socketParams = new StringBuffer();
			 socketParams.append("syzbh=").append(messArray[0])
			 .append("&zcdm=").append(messArray[1])
			 .append("&token=").append(GasWebSocketClient.SOCKET_TOKEN)
			 .append("&pcode=").append(messArray[2])
			 .append("&pid=").append(messArray[3])
			 .append("&pDate=").append(messArray[4])
			 .append("&bfrq=").append(messArray[5]);
			 BarcodegetBottleResMsg barMsg = getBarResMsg(socketParams.toString(),0);
			
			 sengMsg.append( "气瓶使用证编号:").append(barMsg.getResult().getSyzbh())
			 		.append( "气瓶注册代码:").append(barMsg.getResult().getZcdm())
			 		.append( "单位自有编号:").append(barMsg.getResult().getZybh())
			 		.append( "气瓶充装单位(编号):").append(barMsg.getResult().getZybh())
			 		.append( "气瓶制造单位:").append(barMsg.getResult().getPnoName())
			 		.append( "气瓶品种:").append(barMsg.getResult().getClassName())
			 		.append( "气瓶型号:").append(barMsg.getResult().getTypeName())
			 		.append( "充装介质:").append(barMsg.getResult().getMediumName())
			 		.append( "出厂日期:").append(barMsg.getResult().getpDate())
			 		.append( "上检日期:").append(barMsg.getResult().getfDate())
			 		.append( "检验周期:").append(barMsg.getResult().getJyzq()+"年")
			 		.append( "报废年限:").append(barMsg.getResult().getBf())
			 		.append( "下检日期:").append(barMsg.getResult().getXjrq())
			 		.append( "报废日期:").append(barMsg.getResult().getBfrq());
			 		//.append( "气瓶当前状态:").append(barMsg.getResult().getStatus());
		}
		message.setContent(sengMsg.toString());
		// 将消息对象转换成xml
		respXml = MessageService.messageToXml(message);

		return respXml;
	}
	/**
	 * 当token过期或者错误时 重新获得token，然后发送请求
	 * @param socketParams
	 * @param times
	 * @return
	 */
	private BarcodegetBottleResMsg getBarResMsg(String socketParams,int times){
		GasBarcodegetBottleConnect.sendMsg(socketParams.toString());
		String socketMessage = GasBarcodegetBottleClient.message;
		BarcodegetBottleResMsg messageObject = new BarcodegetBottleResMsg();
		messageObject = (BarcodegetBottleResMsg)JSONObject.toBean(JSONObject.fromObject(socketMessage),BarcodegetBottleResMsg.class);
		// token 过期，获取token后重试一次
		if((times) < 1 &(SocketFailCode.CODE_100001 == messageObject.getErrorCode() 
				|| SocketFailCode.CODE_100002 == messageObject.getErrorCode())){
			GasWebSocketUtil.accessWSToken();
			getBarResMsg(socketParams,1);
		} 
		return messageObject;
	}
		
	}

