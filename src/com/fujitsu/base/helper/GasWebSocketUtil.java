/**
 * 
 */
package com.fujitsu.base.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fujitsu.base.client.GasWebSocketConnect;

/**
 * @author Administrator
 *  web Socket client
 */
public class GasWebSocketUtil {
	private static final Logger logger = LoggerFactory.getLogger(GasWebSocketUtil.class);
	static GasWebSocketConnect web = null;
	
	static {
		web = new GasWebSocketConnect();
    	web.start();
    	accessWSToken();
	}
	
	/**
	 * 获取webSocket Token
	 */
	public static void accessWSToken(){
		
    	try {
			web.session.getBasicRemote().sendText("authorizeID="+"o6_bmjrPTlm6_2sgVt7hMZOPfL2Mdddd"+"&authorizeType="+"WebChat_QPSafe");
			System.in.read();
			web.session.close();
    	} catch (Exception e) {
    		logger.error("get web Socket Token Error",e);
		}
	}
}
