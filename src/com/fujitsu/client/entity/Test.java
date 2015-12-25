/**
 * 
 */
package com.fujitsu.client.entity;

import com.fujitsu.base.constants.Const;

import net.sf.json.JSONObject;

/**
 * @author VM
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  /**
         * 处理message 推送给用户的message
         * [QP02001,132020000001,AG,323232,2015年09月,2045年09月]气瓶安全云www.qpsafe.cn
         */
		String scanResult ="[QP02001,132020000001,AG,323232,2015年09月,2045年09月]气瓶安全云www.qpsafe.cn";
		
        int beginIndex = scanResult.lastIndexOf("[");
        int lastIndex = scanResult.lastIndexOf("]");
        // QP02001,132020000001,AG,323232,2015年09月,2045年09月
        String tmp = scanResult.substring(beginIndex+1, lastIndex);
        System.out.print(tmp);
        
       String[] array = "2013-05-06".split("-");
       System.out.print(array.toString());
       String[] array3 = "06".split(" ");
       System.out.print(array3);
       
	}

}
