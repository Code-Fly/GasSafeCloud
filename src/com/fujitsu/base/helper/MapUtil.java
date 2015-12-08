/**
 *
 */
package com.fujitsu.base.helper;

import org.apache.commons.codec.CharEncoding;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author Barrie
 */
public class MapUtil {

    /**
     * 从request中获得参数Map，并返回可读的Map
     *
     * @param request
     * @param isDecode:是否需要解码
     * @return
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request, boolean isDecode) {
        // 参数Map
        Map<String, String[]> properties = request.getParameterMap();
        // 返回值Map
        Map<String, String> returnMap = new HashMap<String, String>();
        Iterator<Entry<String, String[]>> entries = properties.entrySet().iterator();
        Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            if (isDecode) {
                returnMap.put(name, UrlUtil.decode(value, CharEncoding.UTF_8));
            } else {
                returnMap.put(name, value);
            }

        }
        return returnMap;
    }


}
