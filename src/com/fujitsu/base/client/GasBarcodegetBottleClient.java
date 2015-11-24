package com.fujitsu.base.client;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientEndpoint
public class GasBarcodegetBottleClient {
	
private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static String message = "";
	
	@OnOpen
    public void onOpen(Session session) {
		logger.info("To open BarcodegetBottleClient session");
    }
 
	/**
	 * synchronized 同步 防止并发请求 
	 * @param message
	 */
   @OnMessage
   public synchronized  void onMessage(String message) {
	   logger.info("GasBarcodegetBottle message:"+message);
	   GasBarcodegetBottleClient.message = message;
   }
 
   @OnClose
    public void onClose() {
	   logger.info("To close BarcodegetBottleClient session.");
    }
   
   @OnError
   public void onError(Exception ex) {
	   logger.error("BarcodegetBottleClient error:",ex);
   }
   
}
