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

/**
 * @author Administrator
 *通过扫描气瓶二维码获取气瓶最后2笔灌装记录信息
 */
public class GasBarcodegetBottleFillConnect {
private static String uri = "ws://t.qpsafe.cn:9900/ccst_WC_BarcodegetBottleFill";
	
	private static Logger logger = LoggerFactory.getLogger(GasBarcodegetBottleFillConnect.class);
	
	public  static Session session;
	

    static {
    	logger.info("start to connect " + uri);
        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception ex) {
        	logger.info("error" + ex);
        }
 
        try {
            URI r = URI.create(uri);
            session = container.connectToServer(GasBarcodegetBottleFillClient.class, r);
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
}
