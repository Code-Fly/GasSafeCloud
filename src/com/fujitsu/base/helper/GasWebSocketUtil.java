/**
 * 
 */
package com.fujitsu.base.helper;


import com.fujitsu.base.client.GasWebSocketConnect;
import com.fujitsu.base.constants.Const.WebSocket;

/**
 * @author Administrator
 *  web Socket client
 */
public class GasWebSocketUtil {
	
	/**
	 * 获取webSocket Token
	 */
	public static void accessWSToken(){
		GasWebSocketConnect.sengMsg("authorizeID="+WebSocket.AUTHORIZEID+"&authorizeType="+WebSocket.WEB_SOCKET_SLEEP);
	}
}
