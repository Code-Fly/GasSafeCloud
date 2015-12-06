package com.fujitsu.client;

import com.fujitsu.client.entity.CompanyDetailResMsg;
import com.fujitsu.client.entity.CompanyDetailResult;
import com.fujitsu.client.entity.WebSocketResFiled;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Barrie on 15/11/27.
 */
@ClientEndpoint
public class QueryCompanyDetailClient {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static CompanyDetailResMsg messageObject;

    @OnOpen
    public void onOpen(Session session) {
        logger.info("To open QueryCompanyDetailClient session");
    }

    @OnMessage
    public synchronized void onMessage(String message) {
        logger.info("QueryCompanyDetailClient message:" + message);
        CompanyDetailResMsg messageObject = new CompanyDetailResMsg();
        JSONObject object = JSONObject.fromObject(message);
        if (0 != (int) object.get(WebSocketResFiled.ERROR_CODE)) {
            messageObject.setErrorCode((int) object.get(WebSocketResFiled.ERROR_CODE));
            messageObject.setMessage((String) object.get(WebSocketResFiled.MESSAGE));
            QueryCompanyDetailClient.messageObject = messageObject;
        } else {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setRootClass(CompanyDetailResMsg.class);
            Map<String, Class> classMap = new HashMap<String, Class>();
            classMap.put("result", CompanyDetailResult.class);
            jsonConfig.setClassMap(classMap);
            messageObject = (CompanyDetailResMsg) JSONObject.toBean(object, jsonConfig);
            QueryCompanyDetailClient.messageObject = messageObject;
        }
    }

    @OnClose
    public void onClose() {
        logger.info("To close QueryCompanyDetailClient session.");
    }

    @OnError
    public void onError(Throwable throwable) {
        logger.error("QueryCompanyDetailClient error:", throwable);
    }
}
