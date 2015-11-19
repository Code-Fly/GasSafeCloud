/**
 * 
 */
package com.fujitsu.keystone.publics.event;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	
	protected String TO_USER_NAME = "ToUserName";
	
	protected String FROM_USER_NAME = "FromUserName";
	
	protected String EVENT_KEY = "EventKey";
	
	protected String SCAN_RESULT = "ScanResult";
	
	protected String ENTER = System.getProperty("line.separator");
	
	
	public String execute(HttpServletRequest request, Map<String, String> requestMap) throws ConnectionFailedException, AccessTokenException {
		return null;
	}
}
