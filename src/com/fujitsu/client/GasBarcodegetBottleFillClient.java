/**
 * 
 */
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

import com.fujitsu.client.entity.BarcodegetBottleFillResMsg;
import com.fujitsu.client.entity.BarcodegetBottleFillResult;
import com.fujitsu.client.entity.WebSocketResFiled;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author Administrator
 *
 */
@ClientEndpoint
public class GasBarcodegetBottleFillClient {
	private Logger logger = LoggerFactory.getLogger(GasBarcodegetBottleFillClient.class);
	
	public  static BarcodegetBottleFillResMsg  messageObject;
	@OnOpen
    public void onOpen(Session session) {
		logger.info("To open GasBarcodegetBottleFillClient session");
    }
 
	/**
	 * synchronized 同步 防止并发请求 
	 * @param message
	 */
   @OnMessage
   public synchronized  void onMessage(String message) {
	   logger.info("GasBarcodegetBottleFillClient message:"+message);
	  ;
	   BarcodegetBottleFillResMsg messageObject = new BarcodegetBottleFillResMsg();
	   JSONObject object =  JSONObject.fromObject(message);
	   if ( 0 != (int)object.get(WebSocketResFiled.ERROR_CODE)) {
		   messageObject.setErrorCode((int)object.get(WebSocketResFiled.ERROR_CODE));
		   messageObject.setMessage((String)object.get(WebSocketResFiled.MESSAGE));
		   GasBarcodegetBottleFillClient.messageObject = messageObject;
	   } else {
		   JsonConfig jsonConfig = new JsonConfig();
	       jsonConfig.setRootClass(BarcodegetBottleFillResMsg.class);
	       Map<String,Class> classMap = new HashMap<String,Class>();
	       classMap.put("result", BarcodegetBottleFillResult.class);
	       jsonConfig.setClassMap(classMap);
			messageObject = (BarcodegetBottleFillResMsg)JSONObject.toBean(object,jsonConfig);
			GasBarcodegetBottleFillClient.messageObject = messageObject;
	   }
	  
   }
 
   @OnClose
    public void onClose() {
	   logger.info("To close GasBarcodegetBottleFillClient session.");
    }
   
   @OnError
   public void onError(Throwable throwable) {
	   logger.error("GasBarcodegetBottleFillClient error:",throwable);
   }
   
}
