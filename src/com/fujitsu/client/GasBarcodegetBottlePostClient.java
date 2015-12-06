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

import com.fujitsu.client.entity.BarcodegetBottlePostResMsg;
import com.fujitsu.client.entity.BarcodegetBottlePostResult;
import com.fujitsu.client.entity.WebSocketResFiled;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@ClientEndpoint
public class GasBarcodegetBottlePostClient {
	
private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static BarcodegetBottlePostResMsg messageObject;
	
	@OnOpen
    public void onOpen(Session session) {
		logger.info("To open BarcodegetBottlePostClient session");
    }
 
	/**
	 * synchronized 同步 防止并发请求 
	 * @param message
	 */
   @OnMessage
   public synchronized  void onMessage(String message) {
	   logger.info("BarcodegetBottlePostClient message:"+message);
	   BarcodegetBottlePostResMsg messageObject = new BarcodegetBottlePostResMsg();
	   JSONObject object =  JSONObject.fromObject(message);
	   if (0 != (int)object.get(WebSocketResFiled.ERROR_CODE)) {
		   messageObject.setErrorCode((int)object.get(WebSocketResFiled.ERROR_CODE));
		   messageObject.setMessage((String)object.get(WebSocketResFiled.MESSAGE));
		   GasBarcodegetBottlePostClient.messageObject = messageObject;
	   } else {
		   JsonConfig jsonConfig = new JsonConfig();
	       jsonConfig.setRootClass(BarcodegetBottlePostResMsg.class);
	       Map<String,Class> classMap = new HashMap<String,Class>();
	       classMap.put("result", BarcodegetBottlePostResult.class);
	       jsonConfig.setClassMap(classMap);
			messageObject = (BarcodegetBottlePostResMsg)JSONObject.toBean(object,jsonConfig);
		   GasBarcodegetBottlePostClient.messageObject = messageObject;
	   }
   }
 
   @OnClose
    public void onClose() {
	   logger.info("To close BarcodegetBottlePostClient session.");
    }
   
   @OnError
   public void onError(Throwable throwable) {
	   logger.error("BarcodegetBottlePostClient error:",throwable);
   }
   
}
