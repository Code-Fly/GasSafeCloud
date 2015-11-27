package com.fujitsu.base.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

/**
 * Created by Barrie on 15/11/27.
 */
public class QueryCompanyDetailConnect {
    private static String uri = "ws://t.qpsafe.cn:9900/ccst_WC_GetDWInfo";

    private static Logger logger = LoggerFactory.getLogger(QueryCompanyDetailConnect.class);

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
            session = container.connectToServer(QueryCompanyDetailClient.class, r);
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
