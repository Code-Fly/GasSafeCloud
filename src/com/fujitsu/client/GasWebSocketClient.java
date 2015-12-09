package com.fujitsu.client;




import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fujitsu.client.entity.WebSocketResponseMessage;

import net.sf.json.JSONObject;


 @ClientEndpoint
public class GasWebSocketClient {
	 
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static String SOCKET_TOKEN = "";
	
	@OnOpen
    public void onOpen(Session session) {
		logger.info("To open get token session");
    }
 
	/**
	 * synchronized 同步 防止并发请求 
	 * @param message
	 */
   @OnMessage
    public synchronized  void onMessage(String message) {
	    logger.info("Client onMessage: " + message);
	     // 防止重复获得token 
        WebSocketResponseMessage messageObject = new WebSocketResponseMessage();
		messageObject = (WebSocketResponseMessage)JSONObject.toBean(JSONObject.fromObject(message),WebSocketResponseMessage.class);
		if (0== messageObject.getErrorCode()) {
			SOCKET_TOKEN = messageObject.getResult();
		} else {
			SOCKET_TOKEN = "";
		}
		logger.info("SOCKET_TOKEN="+SOCKET_TOKEN);
   }
 
   @OnClose
    public void onClose() {
	   logger.info("To close token session.");
    }
}
