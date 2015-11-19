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
public abstract  class Event {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public String TO_USER_NAME = "ToUserName";
	
	public String FROM_USER_NAME = "FromUserName";
	
	public String EVENT_KEY = "EventKey";
	
	public String SCAN_RESULT = "ScanResult";
	
	public String SCAN_CODE_INFO = "ScanCodeInfo";
	
	public String ENTER = System.getProperty("line.separator");
	
	
	public String execute(HttpServletRequest request, JSONObject requestJson) throws ConnectionFailedException, AccessTokenException {
		return null;
	}
}
