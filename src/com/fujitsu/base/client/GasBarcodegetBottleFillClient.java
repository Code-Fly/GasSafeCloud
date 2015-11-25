/**
 * 
 */
package com.fujitsu.base.client;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fujitsu.base.client.entity.BarcodegetBottleResMsg;
import com.fujitsu.base.client.entity.BarcodegetBottleResult;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author Administrator
 *
 */
public class GasBarcodegetBottleFillClient {
	private Logger logger = LoggerFactory.getLogger(GasBarcodegetBottleFillClient.class);
	
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
		JsonConfig jsonConfig = new JsonConfig();
       jsonConfig.setRootClass(BarcodegetBottleResMsg.class);
       Map<String,Class> classMap = new HashMap<String,Class>();
       classMap.put("result", BarcodegetBottleResult.class);
       jsonConfig.setClassMap(classMap);
		messageObject = (BarcodegetBottleResMsg)JSONObject.toBean(JSONObject.fromObject(message),jsonConfig);
	   GasBarcodegetBottleClient.messageObject = messageObject;
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
