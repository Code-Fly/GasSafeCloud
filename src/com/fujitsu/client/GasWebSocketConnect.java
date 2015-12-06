/**
 * 
 */
package com.fujitsu.client;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fujitsu.base.constants.Const.WebSocket;


/**
 * @author VM
 *
 */
public class GasWebSocketConnect {
	
	private static String uri = WebSocket.URL+"/ccst_getToken";
	
	private static Logger logger = LoggerFactory.getLogger(GasWebSocketConnect.class);
	
	public  static Session session;
 
    static {
    	getSession();
    }
    
    public static void getSession(){
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
    
    public static void sengMsg(String msg){
    	logger.info("msg="+msg);
    	try {
			session.getBasicRemote().sendText(msg);
			 Thread.sleep(WebSocket.WEB_SOCKET_SLEEP);
    	} catch (Exception e) {
    		logger.error("get web Socket Token Error",e);
    		/**
    		 * 发生错误重新获取session后重新发送消息
    		 */
    		getSession();
    		sengMsgTwoTime(msg);
    		
		}
    }
    
    public static void sengMsgTwoTime(String msg){
    	try {
			session.getBasicRemote().sendText(msg);
			 Thread.sleep(WebSocket.WEB_SOCKET_SLEEP);
    	} catch (Exception e) {
    		logger.error("get web Socket TokenTwoTime Error",e);
		}
    }
    
    public static void main(String[] mains){
    
    	try {
    		session.getBasicRemote().sendText("authorizeID=o6_bmjrPTlm6_2sgVt7hMZOPfL2Mdddd&authorizeType=WebChat_QPSafe");
			Thread.sleep(400);
			System.out.println("GasWebSocketClient1."+GasWebSocketClient.SOCKET_TOKEN);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
    
}
