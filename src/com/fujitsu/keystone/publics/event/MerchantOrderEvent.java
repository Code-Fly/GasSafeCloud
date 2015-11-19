/**
 * 
 */
package com.fujitsu.keystone.publics.event;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.base.helper.UrlUtil;
import com.fujitsu.keystone.publics.entity.customer.message.Text;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;
import com.fujitsu.keystone.publics.entity.product.Product;
import com.fujitsu.keystone.publics.service.impl.CustomerService;
import com.fujitsu.keystone.publics.service.impl.ProductService;

/**
 * @author Barrie
 *
 */

public class MerchantOrderEvent extends Event {
	@Override
	public String execute(HttpServletRequest request, JSONObject requestJson) throws ConnectionFailedException, AccessTokenException {
		String at = KeystoneUtil.getAccessToken();

		String respXml = null;

		String fromUserName = requestJson.getString("FromUserName");
		String toUserName = requestJson.getString("ToUserName");
		String orderId = requestJson.getString("OrderId");
		String createTime = requestJson.getString("CreateTime");
		String productId = requestJson.getString("ProductId");

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
