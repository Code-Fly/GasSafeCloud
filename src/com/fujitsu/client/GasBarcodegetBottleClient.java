package com.fujitsu.client;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fujitsu.client.entity.BarcodegetBottleResMsg;
import com.fujitsu.client.entity.BarcodegetBottleResult;
import com.fujitsu.client.entity.WebSocketResFiled;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@ClientEndpoint
public class GasBarcodegetBottleClient {
	
private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static BarcodegetBottleResMsg messageObject;
	
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
	   BarcodegetBottleResMsg messageObject = new BarcodegetBottleResMsg();
	   JSONObject object =  JSONObject.fromObject(message);
	   if ( 0 != (int)object.get(WebSocketResFiled.ERROR_CODE)) {
		   messageObject.setErrorCode((int)object.get(WebSocketResFiled.ERROR_CODE));
		   messageObject.setMessage((String)object.get(WebSocketResFiled.MESSAGE));
		   GasBarcodegetBottleClient.messageObject = messageObject;
	   } else {
		   JsonConfig jsonConfig = new JsonConfig();
	       jsonConfig.setRootClass(BarcodegetBottleResMsg.class);
	       Map<String,Class> classMap = new HashMap<String,Class>();
	       classMap.put("result", BarcodegetBottleResult.class);
	       jsonConfig.setClassMap(classMap);
			messageObject = (BarcodegetBottleResMsg)JSONObject.toBean(object,jsonConfig);
		   GasBarcodegetBottleClient.messageObject = messageObject;
	   }
   }
 
   @OnClose
    public void onClose() {
	   logger.info("To close BarcodegetBottleClient session.");
    }
   
   @OnError
   public void onError(Throwable throwable) {
	   logger.error("BarcodegetBottleClient error:",throwable);
   }
   
}
