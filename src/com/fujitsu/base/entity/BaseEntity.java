/**
 * 
 */
package com.fujitsu.base.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author xieky.fnst
 *
 */
public class BaseEntity {

	@SuppressWarnings("rawtypes")
	public HashMap<String, Object> toHashMap() throws IllegalArgumentException,
			IllegalAccessException {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		Class clazz = this.getClass();
		List<Class> clazzs = new ArrayList<Class>();

		do {
			clazzs.add(clazz);
			clazz = clazz.getSuperclass();
		} while (!clazz.equals(Object.class));

		for (Class iClazz : clazzs) {
			Field[] fields = iClazz.getDeclaredFields();
			for (Field field : fields) {
				Object objVal = null;
				field.setAccessible(true);
				objVal = field.get(this);
				hashMap.put(field.getName(), objVal);
			}
		}

		return hashMap;
	}

	@SuppressWarnings("rawtypes")
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			Class t = this.getClass();
			Field[] fields = t.getDeclaredFields();
			sb.append("{");
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				if (i != 0) {
					sb.append(",");
				}
				sb.append("\"");
				sb.append(field.getName());
				sb.append("\":\"");
				sb.append(field.get(this));
				sb.append("\"");
			}
			sb.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
