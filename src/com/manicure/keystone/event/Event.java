/**
 * 
 */
package com.manicure.keystone.event;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Barrie
 *
 */
public abstract class Event {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public String execute(HttpServletRequest request, Map<String, String> requestMap) {
		return null;
	}
}
