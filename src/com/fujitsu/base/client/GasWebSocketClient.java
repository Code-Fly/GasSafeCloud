package com.fujitsu.base.client;




import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;


 @ClientEndpoint
public class GasWebSocketClient {
	 
	protected Logger logger = LoggerFactory.getLogger(getClass());
	public static String SOCKET_TOKEN = "";
	
	@OnOpen
    public void onOpen(Session session) {
		logger.info("To open get token session");
    }
 
   @OnMessage
    public void onMessage(String message) {
	    logger.info("Client onMessage: " + message);
        WebSocketResponseMessage messageObject = new WebSocketResponseMessage();
		messageObject = (WebSocketResponseMessage)JSONObject.toBean(JSONObject.fromObject(message),WebSocketResponseMessage.class);
		SOCKET_TOKEN = messageObject.getResult().getToken();
		logger.info("SOCKET_TOKEN="+SOCKET_TOKEN);
   }
 
   @OnClose
    public void onClose() {
	   logger.info("To close token session.");
    }
}
