/**
 * 
 */
package com.manicure.base.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * @author Barrie
 *
 */
public class ConfigUtil {
	private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

	public static String getJson(String fileName) throws IOException {
		String path = Const.getServerPath() + "/conf/" + fileName;
		FileInputStream fileInputStream = new FileInputStream(path);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String tempString = null;
		String laststr = "";
		while ((tempString = reader.readLine()) != null) {
			laststr += tempString;
		}
		return laststr;
	}

	public static String getProperty(String fileName, String keyname) {
		Properties p = new Properties();
		String result = "";
		try {
			String path = Const.getServerPath() + "/conf/" + fileName;
			p.load(new FileInputStream(path));
			result = p.getProperty(keyname);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	public static void setProperty(String fileName, String keyname, String keyvalue) {
		Properties p = new Properties();
		try {
			String path = Const.getServerPath() + "/conf/" + fileName;
			p.load(new FileInputStream(path));
			OutputStream fos = new FileOutputStream(path);
			p.setProperty(keyname, keyvalue);
			p.store(fos, "Update '" + keyname + "' value");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	public static void setProperty(String fileName, Map<String, String> map) {
		Properties p = new Properties();
		try {
			String path = Const.getServerPath() + "/conf/" + fileName;
			p.load(new FileInputStream(path));
			OutputStream fos = new FileOutputStream(path);
			p.putAll(map);
			p.store(fos, "Update '" + map + "'");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
