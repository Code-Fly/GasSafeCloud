package com.fujitsu.base.client;

import com.fujitsu.base.client.entity.BarcodegetBottleResMsg;
import com.fujitsu.base.client.entity.BarcodegetBottleResult;
import com.fujitsu.base.client.entity.CompanyListResMsg;
import com.fujitsu.base.client.entity.CompanyListResult;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Barrie on 15/11/26.
 */
public class QueryCompanyListConnect {
    private static String uri = "ws://t.qpsafe.cn:9900/ccst_WC_GetDWList";

    private static Logger logger = LoggerFactory.getLogger(QueryCompanyListConnect.class);

    public static Session session;


    static {
        logger.info("start to connect " + uri);
        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception ex) {
            logger.info("error" + ex);
        }

        try {
            URI r = URI.create(uri);
            session = container.connectToServer(QueryCompanyListClient.class, r);
        } catch (Exception e) {
            logger.error("connectToServer:" + uri, e);
        }
        logger.info("end to connect " + uri);
    }

    public synchronized static void sendMsg(String msg) {
        logger.info("msg = " + msg);
        try {
            session.getBasicRemote().sendText(msg);
            System.in.read();
            Thread.sleep(400);
        } catch (IOException e) {
            logger.error("sendMsg(String msg) error " + uri, e);
        } catch (InterruptedException e) {
            logger.error("sendMsg(String msg) error " + uri, e);
        }
        logger.info("end sendMsg(String msg)");
    }

}
