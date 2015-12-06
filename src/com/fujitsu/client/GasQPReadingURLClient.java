package com.fujitsu.client;

import com.fujitsu.client.entity.GasQPReadingURLResMsg;
import com.fujitsu.client.entity.GasQPReadingURLResult;
import com.fujitsu.client.entity.WebSocketResFiled;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Barrie on 15/12/3.
 */
@ClientEndpoint
public class GasQPReadingURLClient {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static GasQPReadingURLResMsg messageObject;

    @OnOpen
    public void onOpen(Session session) {
        logger.info("To open GasQPReadingURLClient session");
    }

    @OnMessage
    public synchronized void onMessage(String message) {
        logger.info("GasQPReadingURLClient message:" + message);
        GasQPReadingURLResMsg messageObject = new GasQPReadingURLResMsg();
        JSONObject object = JSONObject.fromObject(message);
        if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
            messageObject.setErrorCode((int) object.get(WebSocketResFiled.ERROR_CODE));
            messageObject.setMessage((String) object.get(WebSocketResFiled.MESSAGE));
            GasQPReadingURLClient.messageObject = messageObject;
        } else {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setRootClass(GasQPReadingURLResMsg.class);
            Map<String, Class> classMap = new HashMap<String, Class>();
            classMap.put("result", GasQPReadingURLResult.class);
            jsonConfig.setClassMap(classMap);
            messageObject = (GasQPReadingURLResMsg) JSONObject.toBean(object, jsonConfig);
            GasQPReadingURLClient.messageObject = messageObject;
        }
    }

    @OnClose
    public void onClose() {
        logger.info("To close GasQPReadingURLClient session.");
    }

    @OnError
    public void onError(Throwable throwable) {
        logger.error("GasQPReadingURLClient error:", throwable);
    }
}
