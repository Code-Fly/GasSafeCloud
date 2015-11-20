/**
 * 
 */
package com.fujitsu.keystone.publics.query;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Barrie
 *
 */
public abstract class Query {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static final String FILLING_STORAGE = "CZCC";
	
	public static final String DISTRIBUTION_TRANSPORTATION = "PSYS";
	
	public static final String INSPECTION_TESTING = "JYJC";
	
	public static final String CUSTOMER_SERVICE = "KF";
	
	public static final String SEPARATOR = "#";

	public String execute(HttpServletRequest request, JSONObject requestJson) {
		return null;
	}
}
