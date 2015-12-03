package com.fujitsu.base.client;

import com.fujitsu.base.client.entity.GasQPReadingURLResMsg;
import com.fujitsu.base.client.entity.GasQPReadingURLResult;
import com.fujitsu.base.client.entity.WebSocketResFiled;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;

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
            messageObject = (GasQPReadingURLResMsg) JSONObject.toBean(object, GasQPReadingURLResult.class);
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
