package com.fujitsu.base.util;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author Administrator
 * 
 */
public class CommonUtil {
	/**
	 * Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
	 * 
	 * @param map
	 * @param obj
	 * @throws Exception
	 */
	public static Object transMap2Bean(Map<String, Object> map, Object obj)
			throws Exception {
		BeanUtils.populate(obj, map);
		return obj;
	}

	
}