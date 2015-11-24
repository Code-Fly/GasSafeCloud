/**
 * 
 */
package com.fujitsu.base.client;

import java.io.IOException;
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
    public static void main(String[] ss){
    	StringBuffer socketParams = new StringBuffer();
    	 socketParams.append("syzbh=").append("QP00501")
		 .append("&zcdm=").append("232005001850")
		 .append("&token=").append("Loby9uUhpUb3Z5ApMmxv4RHtgyXCo/vsT6SjKnuzGv4OK7RVIHMGX+VXxeSpQbQhQFDWQGZ3Lc1Zpmz1EIUg4mrQ9Nr6+aqrRLvnxZWjiEw=")
		 .append("&pcode=").append("AY")
		 .append("&pid=").append("00101506.1")
		 .append("&pDate=").append("2006年12月")
		 .append("&bfrq=").append("2026年12月");
    	 try {
			session.getBasicRemote().sendText(socketParams.toString());
			System.in.read();
			session.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 }
