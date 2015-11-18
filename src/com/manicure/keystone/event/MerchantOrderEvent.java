/**
 * 
 */
package com.manicure.keystone.event;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.manicure.base.helper.HttpClientUtil;
import com.manicure.base.helper.KeystoneUtil;
import com.manicure.base.helper.UrlUtil;
import com.manicure.keystone.entity.customer.message.Text;
import com.manicure.keystone.entity.customer.message.TextMessage;
import com.manicure.keystone.entity.product.Product;
import com.manicure.keystone.service.impl.CustomerService;
import com.manicure.keystone.service.impl.ProductService;

/**
 * @author Barrie
 *
 */

public class MerchantOrderEvent extends Event {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.manicure.keystone.event.Event#execute(java.util.Map)
	 */
	@Override
	public String execute(HttpServletRequest request, Map<String, String> requestMap) {
		String at = KeystoneUtil.getAccessToken();
		if (null == at) {
			logger.error(KeystoneUtil.getErrmsg());
			return KeystoneUtil.getErrmsg();
		}

		String respXml = null;

		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		String orderId = requestMap.get("OrderId");
		String createTime = requestMap.get("CreateTime");
		String productId = requestMap.get("ProductId");

		TextMessage message = new TextMessage();
		message.setMsgtype(CustomerService.CUSTOMER_SERVICE_MESSAGE_TYPE_TEXT);
		message.setTouser(fromUserName);
		Text t = new Text();
		StringBuffer buffer = new StringBuffer();
		buffer.append("感谢您付款购买本店的服务！").append("\n");
		buffer.append("\n");
		buffer.append("服务名： ").append("\n");
		Product p = (Product) JSONObject.toBean(new ProductService().getProduct(at, productId), Product.class);
		buffer.append(p.getProduct_info().getProduct_base().getName()).append("\n");
		buffer.append("\n");
		buffer.append("订单编号：").append("\n");
		buffer.append(orderId).append("\n");
		buffer.append("\n");
		buffer.append("下单时间：").append("\n");
		buffer.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.parseLong(createTime + "000"))).append("\n");
		buffer.append("\n");
		buffer.append("如果您有任何疑问，可以直接在下方输入与我们的在线客服联系。").append("\n");
		t.setContent(buffer.toString());
		message.setText(t);

		respXml = new CustomerService().sendTextMessage(at, message).toString();

		String url = UrlUtil.getServerUrl(request, "/api/order/orderextend/add");
		JSONObject params = new JSONObject();
		params.put("buyerOpenid", fromUserName);
		params.put("productId", productId);
		params.put("orderId", orderId);
		String resp = HttpClientUtil.doPostJson(url, params.toString(), "UTF-8");
		if (null == resp) {
			logger.error("fail to post");
		} else {
			logger.info(resp);
		}

		return respXml;
	}

}
