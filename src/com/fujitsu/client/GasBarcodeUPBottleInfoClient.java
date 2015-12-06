package com.fujitsu.client;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fujitsu.client.entity.WebSocketResponseMessage;

import net.sf.json.JSONObject;

@ClientEndpoint
public class GasBarcodeUPBottleInfoClient {
	
private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static WebSocketResponseMessage messageObject;
	
	@OnOpen
    public void onOpen(Session session) {
		logger.info("To open GasBarcodeUPBottleInfoClient session");
    }
 
	/**
	 * synchronized 同步 防止并发请求 
	 * @param message
	 */
   @OnMessage
   public synchronized  void onMessage(String message) {
	   logger.info("GasGasBarcodeUPBottleInfo message:"+message);
		messageObject = (WebSocketResponseMessage)JSONObject.toBean(JSONObject.fromObject(message),WebSocketResponseMessage.class);
   }
 
   @OnClose
    public void onClose() {
	   logger.info("To close GasBarcodeUPBottleInfoClient session.");
    }
   
   @OnError
   public void onError(Throwable throwable) {
	   logger.error("GasBarcodeUPBottleInfoClient error:",throwable);
   }
   
}
