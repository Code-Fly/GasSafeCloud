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
	public static String encode(String source, String charset) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String decode(String source, String charset) {
		String result = source;
		try {
			result = java.net.URLDecoder.decode(source, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
