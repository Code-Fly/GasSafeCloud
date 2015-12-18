/**
 *
 */
package com.fujitsu.base.helper;

import com.fujitsu.base.constants.Const;
import org.apache.commons.codec.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * @author Barrie
 */
public class ConfigUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

    public static String getJson(String fileName) {
        String path = Const.getServerPath() + "conf/" + fileName;
        String tempString = null;
        String lastStr = "";

        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, CharEncoding.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            while ((tempString = reader.readLine()) != null) {
                lastStr += tempString;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return lastStr;
    }

    public static String getProperty(String fileName, String keyname) {
        Properties p = new Properties();
        String result = "";
        try {
            String path = Const.getServerPath() + "conf/" + fileName;
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
            String path = Const.getServerPath() + "conf/" + fileName;
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
            String path = Const.getServerPath() + "conf/" + fileName;
            p.load(new FileInputStream(path));
            OutputStream fos = new FileOutputStream(path);
            p.putAll(map);
            p.store(fos, "Update '" + map + "'");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
