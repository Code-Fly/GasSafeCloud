package com.fujitsu.keystone.publics.event;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.entity.customer.message.Text;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;
import com.fujitsu.keystone.publics.service.impl.CustomerService;
import com.fujitsu.keystone.publics.service.impl.MenuService;

public class ScancodeWaitmsgEvent extends Event {
	
	/**
	 * @throws AccessTokenException 
	 * @throws ConnectionFailedException 
	 * 
	 */
	public String execute(HttpServletRequest request, Map<String, String> requestMap) throws ConnectionFailedException, AccessTokenException {
		String at = KeystoneUtil.getAccessToken();

		String respXml = null;

		String fromUserName = requestMap.get(FROM_USER_NAME);
		String scanResult = requestMap.get(SCAN_RESULT);
		
		TextMessage message = new TextMessage();
		message.setMsgtype(CustomerService.CUSTOMER_SERVICE_MESSAGE_TYPE_TEXT);
		message.setTouser(fromUserName);
		Text t = new Text();
		
		/**处理message
		 * 推送给用户的message
		 * [QP02001,132020000001,AG,323232,2015年09月,2045年09月]气瓶安全云www.qpsafe.cn
		 */
		int lastIndex = scanResult.lastIndexOf("]");
		// QP02001,132020000001,AG,323232,2015年09月,2045年09月
		String tmp = scanResult.substring(1,lastIndex);
		// [QP02001,132020000001,AG,323232,2015年09月,2045年09月]
		String[] messArray = tmp.split(",");
		StringBuffer mess = new StringBuffer();
		// 身份查询
		if(MenuService.QP_SFCX.equals(requestMap.get(EVENT_KEY))){
			mess.append("气瓶使用证编号:").append(messArray[0]).append(ENTER)
			.append("气瓶注册代码:").append(messArray[1]).append(ENTER)
			.append("气瓶充装单位(编号):").append(messArray[3]).append(ENTER)
			.append("气瓶编号:").append(messArray[3]).append(ENTER)
			.append("出厂日期:").append(messArray[3]).append(ENTER)
			.append("报废日期:").append(messArray[3]).append(ENTER);
		}
		t.setContent(mess.toString());
		respXml = new CustomerService().sendTextMessage(at, message).toString();
		return respXml;
		
	}
}
