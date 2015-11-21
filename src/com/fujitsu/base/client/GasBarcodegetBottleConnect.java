/**
 * 
 */
package com.fujitsu.base.client;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author VM
 *
 */
public class GasBarcodegetBottleConnect {
private static String uri = "ws://t.qpsafe.cn:9900/ccst_WC_BarcodegetBottle";
	
	private static Logger logger = LoggerFactory.getLogger(GasBarcodegetBottleConnect.class);
	
	public  static Session session;
	
    static  {
    	logger.info("start to connect " + uri);
        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception ex) {
        	logger.info("error" + ex);
        }
 
        try {
            URI r = URI.create(uri);
            session = container.connectToServer(GasBarcodegetBottleClient.class, r);
        } catch (Exception e) {
        	logger.error("connectToServer:"+uri,e);
        }
        logger.info("end to connect " + uri);
    }
 }