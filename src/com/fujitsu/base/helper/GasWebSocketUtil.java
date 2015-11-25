/**
 * 
 */
package com.fujitsu.base.helper;


import com.fujitsu.base.client.GasWebSocketConnect;

/**
 * @author Administrator
 *  web Socket client
 */
public class GasWebSocketUtil {
	
	static {
		accessWSToken();
	}
	/**
	 * 获取webSocket Token
	 */
	public static void accessWSToken(){
		GasWebSocketConnect.sengMsg("authorizeID="+"o6_bmjrPTlm6_2sgVt7hMZOPfL2Mdddd"+"&authorizeType="+"WebChat_QPSafe");
	}
}
