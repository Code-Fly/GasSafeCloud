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

import com.fujitsu.client.entity.BarcodegetBottlePsafeResMsg;
import com.fujitsu.client.entity.BarcodegetBottlePsafeResult;
import com.fujitsu.client.entity.WebSocketResFiled;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@ClientEndpoint
public class GasBarcodegetBottlePsafeClient {
	
private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static BarcodegetBottlePsafeResMsg messageObject;
	
	@OnOpen
    public void onOpen(Session session) {
		logger.info("To open GasBarcodegetBottlePsafeClient session");
    }
 
	/**
	 * synchronized 同步 防止并发请求 
	 * @param message
	 */
   @OnMessage
   public synchronized  void onMessage(String message) {
	   logger.info("GasBarcodegetBottlePsafeClient message:"+message);
	   BarcodegetBottlePsafeResMsg messageObject = new BarcodegetBottlePsafeResMsg();
	   JSONObject object =  JSONObject.fromObject(message);
	   if (0 != (int)object.get(WebSocketResFiled.ERROR_CODE)) {
		   messageObject.setErrorCode((int)object.get(WebSocketResFiled.ERROR_CODE));
		   messageObject.setMessage((String)object.get(WebSocketResFiled.MESSAGE));
		   GasBarcodegetBottlePsafeClient.messageObject = messageObject;
	   } else {
		   JsonConfig jsonConfig = new JsonConfig();
	       jsonConfig.setRootClass(BarcodegetBottlePsafeResMsg.class);
	       Map<String,Class> classMap = new HashMap<String,Class>();
	       classMap.put("result", BarcodegetBottlePsafeResult.class);
	       jsonConfig.setClassMap(classMap);
			messageObject = (BarcodegetBottlePsafeResMsg)JSONObject.toBean(object,jsonConfig);
		   GasBarcodegetBottlePsafeClient.messageObject = messageObject;
	   }
   }
 
   @OnClose
    public void onClose() {
	   logger.info("To close GasBarcodegetBottlePsafeClient session.");
    }
   
   @OnError
   public void onError(Throwable throwable) {
	   logger.error("GasBarcodegetBottlePsafeClient error:",throwable);
   }
   
}
