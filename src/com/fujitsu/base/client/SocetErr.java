/**
 * 
 */
package com.fujitsu.base.client;

/**
 * @author VM
 *
 */
public enum SocetErr {
	
	ERR_100001(SocketFailCode.CODE_100001,"无效验证token"),
	ERR_100002(SocketFailCode.CODE_100002,"验证token过期");
	
	public int errorCode;
	public String message;
	SocetErr(int errorCode, String message){
		this.errorCode = errorCode;
		this.message = message;
	}
	
}
