package com.fujitsu.base.client;

import com.fujitsu.base.client.entity.BarcodegetBottleResMsg;
import com.fujitsu.base.client.entity.BarcodegetBottleResult;
import com.fujitsu.base.client.entity.WebSocketResFiled;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Barrie on 15/11/26.
 */
@ClientEndpoint
public class QueryCompanyListClient {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static BarcodegetBottleResMsg messageObject;

    @OnOpen
    public void onOpen(Session session) {
        logger.info("To open BarcodegetBottleClient session");
    }

    @OnMessage
    public synchronized void onMessage(String message) {
        logger.info("GasBarcodegetBottle message:" + message);
        BarcodegetBottleResMsg messageObject = new BarcodegetBottleResMsg();
        JSONObject object = JSONObject.fromObject(message);
        if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
            messageObject.setErrorCode((int) object.get(WebSocketResFiled.ERROR_CODE));
            messageObject.setMessage((String) object.get(WebSocketResFiled.MESSAGE));
            GasBarcodegetBottleClient.messageObject = messageObject;
        } else {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setRootClass(BarcodegetBottleResMsg.class);
            Map<String, Class> classMap = new HashMap<String, Class>();
            classMap.put("result", BarcodegetBottleResult.class);
            jsonConfig.setClassMap(classMap);
            messageObject = (BarcodegetBottleResMsg) JSONObject.toBean(object, jsonConfig);
            GasBarcodegetBottleClient.messageObject = messageObject;
        }
    }

    @OnClose
    public void onClose() {
        logger.info("To close BarcodegetBottleClient session.");
    }

    @OnError
    public void onError(Throwable throwable) {
        logger.error("BarcodegetBottleClient error:", throwable);
    }
}
