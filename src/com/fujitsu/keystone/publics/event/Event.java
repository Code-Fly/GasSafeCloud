/**
 * 
 */
package com.fujitsu.keystone.publics.event;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;

/**
 * @author Barrie
 *
 */
public abstract class Event {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static String TO_USER_NAME = "ToUserName";

	public static String FROM_USER_NAME = "FromUserName";
	
	public static String CREATE_TIME = "CreateTime";
	
	public static String MSG_TYPE = "MsgType";
	
	public static String EVENT = "Event";

	
	

	

	

	public static String ENTER = System.getProperty("line.separator");

	public String execute(HttpServletRequest request, JSONObject requestJson) throws ConnectionFailedException, AccessTokenException {
		return null;
	}
}
