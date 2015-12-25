package com.fujitsu.base.helper;

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

	/**
	 * 2015-12-25
	 * @param date
	 * @return 2015年12月
	 */
	public static String dateToYearMonth(String date) {
		if(null != date && 1 < date.split("-").length){
			String[] array = date.split("-");
			return array[0] + "年" + array[1] + "月";
		} else {
			return date;
		}
	}
	
	
	/**
	 * 2015-12-1 12:00:01
	 * @param date
	 * @return 2015年12月1日  12:00:01
	 */
	public static String dateToYearMonthDay(String date) {
		if(null != date && 2 < date.split("-").length){
			String[] array = date.split("-");
			if(array[2].split(" ").length == 2){
				String[] dayTime = array[2].split(" ");
				return array[0] + "年" + array[1] + "月" + dayTime[0] + "日" + dayTime[1];
			} else {
				return array[0] + "年" + array[1] + "月" + array[2] + "日";
			}
		
		} else {
			return date;
		}
	}
}