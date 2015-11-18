/**
 * 
 */
package com.manicure.keystone.event;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.jasper.runtime.ProtectedFunctionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	
	public String execute(HttpServletRequest request, Map<String, String> requestMap) {
		return null;
	}
}
