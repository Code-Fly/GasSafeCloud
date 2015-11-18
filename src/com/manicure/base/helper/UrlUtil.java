/**
 * 
 */
package com.manicure.base.helper;

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
	
	/**
	 * URL编码（utf-8）
	 * 
	 * @param source
	 * @return
	 */
	public static String toUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
