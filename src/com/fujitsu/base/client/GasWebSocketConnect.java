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
public class GasWebSocketConnect {
	
	private static String uri = "ws://t.qpsafe.cn:9900/ccst_getToken";
	
	private static Logger logger = LoggerFactory.getLogger(GasWebSocketConnect.class);
	
	public  Session session;
 
    public void start() {
    	logger.info("start to connect " + uri);
        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception ex) {
        	logger.info("error" + ex);
        }
 
        try {
            URI r = URI.create(uri);
            session = container.connectToServer(GasWebSocketClient.class, r);
        } catch (Exception e) {
        	logger.error("connectToServer:"+uri,e);
        }
        logger.info("end to connect " + uri);
    }
    
    public static void main(String[] mains){
    	GasWebSocketConnect web = new GasWebSocketConnect();
    	web.start();
    	try {
			web.session.getBasicRemote().sendText("authorizeID=o6_bmjrPTlm6_2sgVt7hMZOPfL2Mdddd&authorizeType=WebChat_QPSafe");
			System.in.read();
			web.session.close();
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
    
    
}
