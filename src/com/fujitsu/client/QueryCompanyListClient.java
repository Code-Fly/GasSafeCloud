package com.fujitsu.client;

import com.fujitsu.client.entity.*;
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

    public static CompanyListResMsg messageObject;

    @OnOpen
    public void onOpen(Session session) {
        logger.info("To open QueryCompanyListClient session");
    }

    @OnMessage
    public synchronized void onMessage(String message) {
        logger.info("QueryCompanyListClient message:" + message);
        CompanyListResMsg messageObject = new CompanyListResMsg();
        JSONObject object = JSONObject.fromObject(message);
        if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
            messageObject.setErrorCode((int) object.get(WebSocketResFiled.ERROR_CODE));
            messageObject.setMessage((String) object.get(WebSocketResFiled.MESSAGE));
            QueryCompanyListClient.messageObject = messageObject;
        } else {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setRootClass(CompanyListResMsg.class);
            Map<String, Class> classMap = new HashMap<String, Class>();
            classMap.put("result", CompanyListResult.class);
            jsonConfig.setClassMap(classMap);
            messageObject = (CompanyListResMsg) JSONObject.toBean(object, jsonConfig);
            QueryCompanyListClient.messageObject = messageObject;
        }
    }

    @OnClose
    public void onClose() {
        logger.info("To close QueryCompanyListClient session.");
    }

    @OnError
    public void onError(Throwable throwable) {
        logger.error("QueryCompanyListClient error:", throwable);
    }
}
