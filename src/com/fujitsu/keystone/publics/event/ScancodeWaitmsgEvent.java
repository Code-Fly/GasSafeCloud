package com.fujitsu.keystone.publics.event;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.fujitsu.base.client.GasBarcodegetBottleClient;
import com.fujitsu.base.client.GasBarcodegetBottleConnect;
import com.fujitsu.base.client.GasBarcodegetBottleFillClient;
import com.fujitsu.base.client.GasBarcodegetBottleFillConnect;
import com.fujitsu.base.client.GasWebSocketClient;
import com.fujitsu.base.client.entity.BarcodegetBottleFillResMsg;
import com.fujitsu.base.client.entity.BarcodegetBottleResMsg;
import com.fujitsu.base.client.entity.SocketFailCode;
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
			if (0 == barMsg.getErrorCode()) {
				sengMsg.append( "气瓶使用证编号:").append(barMsg.getResult().get(0).getSyzbh())
		 		.append( "气瓶注册代码:").append(barMsg.getResult().get(0).getZcdm()).append(ENTER)
		 		.append( "单位自有编号:").append(barMsg.getResult().get(0).getZybh()).append(ENTER)
		 		.append( "气瓶充装单位(编号):").append(barMsg.getResult().get(0).getZybh()).append(ENTER)
		 		.append( "气瓶制造单位:").append(barMsg.getResult().get(0).getPnoName()).append(ENTER)
		 		.append( "气瓶品种:").append(barMsg.getResult().get(0).getClassName()).append(ENTER)
		 		.append( "气瓶型号:").append(barMsg.getResult().get(0).getTypeName()).append(ENTER)
		 		.append( "充装介质:").append(barMsg.getResult().get(0).getMediumName()).append(ENTER)
		 		.append( "出厂日期:").append(barMsg.getResult().get(0).getpDate()).append(ENTER)
		 		.append( "上检日期:").append(barMsg.getResult().get(0).getfDate()).append(ENTER)
		 		.append( "检验周期:").append(barMsg.getResult().get(0).getJyzq()+"年").append(ENTER)
		 		.append( "报废年限:").append(barMsg.getResult().get(0).getBf()).append(ENTER)
		 		.append( "下检日期:").append(barMsg.getResult().get(0).getXjrq()).append(ENTER)
		 		.append( "报废日期:").append(barMsg.getResult().get(0).getBfrq());
			} else {
				sengMsg.append("系统请求socket出现异常:").append(barMsg.getErrorCode());
			}
			 
		} else if (MenuService.QP_GZJL.equals(eventKey)) {
			  StringBuffer socketParams = new StringBuffer();
				 socketParams.append("syzbh=").append(messArray[0])
				 .append("&zcdm=").append(messArray[1])
				 .append("&token=").append(GasWebSocketClient.SOCKET_TOKEN)
				 .append("&pcode=").append(messArray[2])
				 .append("&pid=").append(messArray[3])
				 .append("&pDate=").append(messArray[4])
				 .append("&bfrq=").append(messArray[5]);
				 
				 BarcodegetBottleFillResMsg barMsg = getBottleResMsg(socketParams.toString(),0);
					if (0 == barMsg.getErrorCode()) {
						sengMsg.append( "气瓶编号 :").append(barMsg.getResult().get(0).getPid()).append(ENTER)
				 		.append( "气瓶制造单位:").append(barMsg.getResult().get(0).getpCode()).append(ENTER)
				 		.append( "最后一次充装日期:").append(barMsg.getResult().get(0).getCheckDatetimeStart()).append(ENTER)
				 		.append( "充装时间:").append(barMsg.getResult().get(0).getCheckDatetimeEnd()).append(ENTER)
				 		//.append( "气瓶型号:").append(barMsg.getResult().get(0).getClassName())
				 		.append( "充装量:").append(barMsg.getResult().get(0).getFillWeight()).append(ENTER)
				 		//.append( "充装单位:").append(barMsg.getResult().get(0).getMediumName())
				 		//.append( "充装单位许可证号:").append(barMsg.getResult().get(0).getpDate())
				 		.append( "充装工:").append(barMsg.getResult().get(0).getWorkNum()).append(ENTER)
				 		//.append( "作业证号:").append(barMsg.getResult().get(0).getJyzq()+"年")
				 		.append( "最后二次充装日期:").append(barMsg.getResult().get(1).getCheckDatetimeStart()).append(ENTER)
				 		.append( "充装时间:").append(barMsg.getResult().get(1).getCheckDatetimeEnd()).append(ENTER)
				 		//.append( "气瓶型号:").append(barMsg.getResult().get(0).getClassName())
				 		.append( "充装量:").append(barMsg.getResult().get(1).getFillWeight()).append(ENTER)
				 		//.append( "充装单位:").append(barMsg.getResult().get(0).getMediumName())
				 		//.append( "充装单位许可证号:").append(barMsg.getResult().get(0).getpDate())
				 		.append( "充装工:").append(barMsg.getResult().get(1).getWorkNum()).append(ENTER);
				 		//.append( "作业证号:").append(barMsg.getResult().get(0).getJyzq()+"年")
				 		
					} else {
						sengMsg.append("系统请求socket出现异常:").append(barMsg.getErrorCode());
					}
		}
		logger.info("setContent:"+sengMsg.toString());
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
		logger.info("getBarResMsg times="+times);
		Long systemTime = System.currentTimeMillis();
		logger.info("system time :="+systemTime);
		GasBarcodegetBottleConnect.sendMsg(socketParams.toString());
		logger.info("system time :="+(System.currentTimeMillis()-systemTime));
		BarcodegetBottleResMsg messageObject = GasBarcodegetBottleClient.messageObject;
		if((times) < 1 &&(SocketFailCode.CODE_100001 == messageObject.getErrorCode() 
				|| SocketFailCode.CODE_100002 == messageObject.getErrorCode())){
			logger.info("Bar times="+times);
			GasWebSocketUtil.accessWSToken();
			getBarResMsg(socketParams,1);
		} 
		logger.info("getBarResMsg messageObject="+messageObject.getMessage());
		return messageObject;
	}
	
	/**
	 * 当token过期或者错误时 重新获得token，然后发送请求
	 * @param socketParams
	 * @param times
	 * @return
	 */
	private BarcodegetBottleFillResMsg getBottleResMsg(String socketParams,int times){
		logger.info("getBottleResMsg times="+times);
		GasBarcodegetBottleFillConnect.sendMsg(socketParams.toString());
		BarcodegetBottleFillResMsg messageObject = GasBarcodegetBottleFillClient.messageObject;
		if((times) < 1 &&(SocketFailCode.CODE_100001 == messageObject.getErrorCode() 
				|| SocketFailCode.CODE_100002 == messageObject.getErrorCode())){
			logger.info("Bottle times="+times);
			GasWebSocketUtil.accessWSToken();
			getBottleResMsg(socketParams,1);
		} 
		logger.info("getBottleResMsg messageObject="+messageObject.getMessage());
		return messageObject;
	}
		
	}

