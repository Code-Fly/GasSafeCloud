/**
 * 
 */
package com.fujitsu.base.helper;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Barrie
 *
 */
public class UrlUtil {
	public static String getServerUrl(HttpServletRequest request, String target) {
		String path = request.getContextPath();
		int port = request.getServerPort();
		String basePath = null;
		if (80 == port) {
			basePath = request.getScheme() + "://" + request.getServerName() + path;
		} else {
			basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		}

		String url = basePath + target;
		return url;
	}

	public static String encode(String source, String chartset) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, chartset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String decode(String source, String chartset) {
		String result = source;
		try {
			result = java.net.URLDecoder.decode(source, chartset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
