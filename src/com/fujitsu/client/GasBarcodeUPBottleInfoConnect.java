/**
 * 
 */
package com.fujitsu.client;


import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.constants.Const.WebSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author VM
 *
 */
public class GasBarcodeUPBottleInfoConnect {
	private static String uri = WebSocket.URL+"/ccst_WC_BarcodeUPBottleInfo";
	
	private static Logger logger = LoggerFactory.getLogger(GasBarcodeUPBottleInfoConnect.class);
	
	public  static Session session;
	

    static {
    	getSession();
    }
    
    public static void getSession(){
    	logger.info("start to connect " + uri);
        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception ex) {
        	logger.info("error" + ex);
        }
 
        try {
            URI r = URI.create(uri);
            session = container.connectToServer(GasBarcodeUPBottleInfoClient.class, r);
        } catch (Exception e) {
        	logger.error("connectToServer:"+uri,e);
        }
        logger.info("end to connect " + uri);
    }
    
	public synchronized static  void sendMsg(String msg){
		logger.info("msg = " + msg);
		try {
			session.getBasicRemote().sendText(msg);
			Thread.sleep(Const.WebSocket.WEB_SOCKET_SLEEP);
			System.in.read();
		} catch (Exception e) {
			logger.error("sendMsg(String msg) error " + uri,e);
			getSession();
    		sengMsgTwoTime(msg);
		} 
		logger.info("end sendMsg(String msg)");
	}
	
	public synchronized static  void sengMsgTwoTime(String msg){
		logger.info("msg = " + msg);
		try {
			session.getBasicRemote().sendText(msg);
			Thread.sleep(Const.WebSocket.WEB_SOCKET_SLEEP);
			 System.in.read();
		} catch (Exception e) {
			logger.error("sendMsg(String msg) error " + uri,e);
		} 
		logger.info("end sendMsg(String msg)");
	}
 }
