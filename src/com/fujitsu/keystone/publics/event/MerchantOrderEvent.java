/**
 * 
 */
package com.fujitsu.keystone.publics.event;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

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
	public static String PRODUCT_ID = "ProductId";

	public static String ORDER_ID = "OrderId";

	@Override
	public String execute(HttpServletRequest request, JSONObject requestJson) throws ConnectionFailedException, AccessTokenException, WeChatException {
		String at = KeystoneUtil.getAccessToken();

		String respXml = null;

		// 发送方帐号
		String fromUserName = requestJson.getString(FROM_USER_NAME);
		// 开发者微信号
		String toUserName = requestJson.getString(TO_USER_NAME);
		String createTime = requestJson.getString(CREATE_TIME);
		String orderId = requestJson.getString(ORDER_ID);

		String productId = requestJson.getString(PRODUCT_ID);

		TextMessage message = new TextMessage();
		message.setMsgtype(CustomerService.CUSTOMER_SERVICE_MESSAGE_TYPE_TEXT);
		message.setTouser(fromUserName);
		Text t = new Text();
		StringBuffer buffer = new StringBuffer();
		buffer.append("感谢您付款购买本店的服务！").append(ENTER);
		buffer.append(ENTER);
		buffer.append("服务名： ").append(ENTER);
		Product p = (Product) JSONObject.toBean(new ProductService().getProduct(at, productId), Product.class);
		buffer.append(p.getProduct_info().getProduct_base().getName()).append(ENTER);
		buffer.append(ENTER);
		buffer.append("订单编号：").append(ENTER);
		buffer.append(orderId).append(ENTER);
		buffer.append(ENTER);
		buffer.append("下单时间：").append(ENTER);
		buffer.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.parseLong(createTime + "000"))).append(ENTER);
		buffer.append(ENTER);
		buffer.append("如果您有任何疑问，可以直接在下方输入与我们的在线客服联系。").append(ENTER);
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
			throw new ConnectionFailedException();
		}
		logger.info(resp);

		return respXml;
	}

}
