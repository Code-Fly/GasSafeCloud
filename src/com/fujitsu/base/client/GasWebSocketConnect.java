/**
 * 
 */
package com.fujitsu.base.client;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * @author VM
 *
 */
public class GasWebSocketConnect {
	
	private static String uri = "ws://t.qpsafe.cn:9900/ccst_getToken";
	
    private static Session session;
 
    private void start() {
        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception ex) {
            System.out.println("error" + ex);
        }
 
        try {
            URI r = URI.create(uri);
            session = container.connectToServer(GasWebSocketClient.class, r);
        } catch (DeploymentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void main(String[] mains){
    	GasWebSocketConnect web = new GasWebSocketConnect();
    	web.start();
    	try {
			web.session.getBasicRemote().sendText("authorizeID=WebChat_QPSafe&authorizeType=o6_bmjrPTlm6_2sgVt7hMZOPfL2Mdddd");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
}
