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
	
	/**
	 * 获取webSocket Token
	 */
	public static void accessWSToken(){
		GasWebSocketConnect web = new GasWebSocketConnect();
    	web.start();
    	try {
			web.session.getBasicRemote().sendText("authorizeID="+Const.AUTHORIZEID+"&authorizeType="+Const.AUTHORIZETYPE);
			System.in.read();
			web.session.close();
    	} catch (Exception e) {
    		logger.error("get web Socket Token Error",e);
		}
	}
}
