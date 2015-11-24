/**
 * 
 */
package com.fujitsu.base.client;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;


/**
 * @author VM
 *
 */
public class GasBarcodegetBottleConnect {
	private static String uri = "ws://t.qpsafe.cn:9900/ccst_WC_BarcodegetBottle";
	
	private static Logger logger = LoggerFactory.getLogger(GasBarcodegetBottleConnect.class);
	
	public  static Session session;
	
	static  {
    	logger.info("start to connect " + uri);
        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception ex) {
        	logger.info("error" + ex);
        }
        try {
            URI r = URI.create(uri);
            session = container.connectToServer(GasBarcodegetBottleClient.class, r);
        } catch (Exception e) {
        	logger.error("connectToServer:"+uri,e);
        }
        logger.info("end to connect " + uri);
    }
	
	public static void sendMsg(String msg){
		logger.info("msg = " + msg);
		try {
			session.getBasicRemote().sendText(msg);
			System.in.read();
		} catch (IOException e) {
			logger.error("sendMsg(String msg) error " + uri,e);
		} 
		logger.info("end sendMsg(String msg)");
	}
	
	public static void main(String[] ss){
		StringBuffer socketParams = new StringBuffer();
		socketParams.append("syzbh=").append("QP00101")
		 .append("&zcdm=").append("232001000002")
		 .append("&token=").append("Loby9uUhpUb3Z5ApMmxv4RHtgyXCo/vsT6SjKnuzGv4OK7RVIHMGX+VXxeSpQbQh3W/UM8tRdIyPDaPWXGLgaGrQ9Nr6+aqrRLvnxZWjiEw=")
		 .append("&pcode=").append("AN")
		 .append("&pid=").append("3211231")
		 .append("&pDate=").append("2015年9月")
		 .append("&bfrq=").append("2035年9月");
		//sendMsg(socketParams.toString());
		//String socketMessage = GasBarcodegetBottleClient.message;
		BarcodegetBottleResMsg messageObject = new BarcodegetBottleResMsg();
		messageObject = (BarcodegetBottleResMsg)JSONObject.toBean(JSONObject.fromObject("{\"message\":\"Success\",\"result\":[{\"bf\":20,\"bfrq\":\"2035-09-01 00:00:00\",\"className\":\"钢质焊接气瓶\",\"dimension\":30,\"fDate\":\"2015-09-01 00:00:00\",\"jyzq\":2,\"mediumName\":\"环氧乙烷\",\"ownName\":\"自有\",\"pDate\":\"2015-09-01 00:00:00\",\"pPress\":30,\"pid\":\"3211231\",\"ply\":3,\"pnoName\":\"青岛钢瓶厂\",\"press\":30,\"qpStructureName\":\"焊接气瓶\",\"quality\":30,\"regTime\":\"2015-11-24 00:00:00\",\"rno\":\"CCST32A0001\",\"rnoidTzsbName\":\"江苏省无锡质量技术监督局\",\"status\":0,\"syzbh\":\"QP00101\",\"typeName\":\"中容积\",\"xjrq\":\"2017-09-01 00:00:00\",\"zcdm\":\"232001000002\",\"zybh\":\"32A00012320003000002\"}],\"errorCode\":0}"),BarcodegetBottleResMsg.class);
System.out.println(messageObject.getResult().getBfrq());
	}
 }
