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
public class GasBarcodegetBottleConnect {
	private static String uri = WebSocket.URL+"/ccst_WC_BarcodegetBottle";
	
	private static Logger logger = LoggerFactory.getLogger(GasBarcodegetBottleConnect.class);
	
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
            session = container.connectToServer(GasBarcodegetBottleClient.class, r);
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
	
	public static void main(String[] ss){
		StringBuffer socketParams = new StringBuffer();
		socketParams.append("syzbh=").append("QP00101")
		 .append("&zcdm=").append("232001000002")
		 .append("&token=").append("Loby9uUhpUb3Z5ApMmxv4RHtgyXCo/vsT6SjKnuzGv4OK7RVIHMGX+VXxeSpQbQha75tBW5uo57XLRRUoEoRK2rQ9Nr6+aqrRLvnxZWjiEw=")
		 .append("&pcode=").append("AN")
		 .append("&pid=").append("3211231")
		 .append("&pDate=").append("2015年9月")
		 .append("&bfrq=").append("2035年9月");
		sendMsg(socketParams.toString());
		System.out.println("GasBarcodegetBottleClient.messageObject:"+GasBarcodegetBottleClient.messageObject.getErrorCode());;
	}
 }
