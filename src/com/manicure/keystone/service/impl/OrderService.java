/**
 * 
 */
package com.manicure.keystone.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.manicure.base.helper.Const;
import com.manicure.base.helper.FileUtil;
import com.manicure.base.helper.HttpClientUtil;
import com.manicure.base.service.BaseService;
import com.manicure.keystone.entity.error.ErrorMsg;
import com.manicure.keystone.entity.order.Order;
import com.manicure.keystone.entity.order.OrderBase;
import com.manicure.keystone.entity.order.OrderList;
import com.manicure.keystone.entity.product.ProductInfo;
import com.manicure.keystone.service.iface.ICoreService;
import com.manicure.keystone.service.iface.IOrderService;

/**
 * @author Barrie
 *
 */
@Service
public class OrderService extends BaseService implements IOrderService {
	@Resource
	ICoreService coreService;

	public final int STATUS_ALL = 0;
	public final int STATUS_NOT_SHIPPED = 2;
	public final int STATUS_SHIPPED = 3;
	public final int STATUS_RETURING = 8;
	public final int STATUS_DONE = 5;

	public JSONObject getOrderList(String accessToken, String status, String beginTime, String endTime) {
		String url = URL_ORDER_GET_LIST.replace("ACCESS_TOKEN", accessToken);
		JSONObject request = new JSONObject();
		if (!"0".equals(status)) {
			request.put("status", status);
		}
		if (!"0".equals(beginTime)) {
			request.put("begintime", beginTime);
		}

		if (!"0".equals(endTime)) {
			request.put("endtime", endTime);
		}

		JSONObject response = HttpClientUtil.doHttpsRequest(url, "GET", request.toString());

		if (null == response) {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrcode("-1");
			errMsg.setErrmsg("server is busy");

			return JSONObject.fromObject(errMsg);
		}
		return response;
	}

	public JSONObject getOrderList(HttpServletRequest request, String accessToken, String status, String beginTime, String endTime) {
		JSONObject resp = getOrderList(accessToken, status, beginTime, endTime);
		if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
			logger.error(resp.toString());
			return resp;
		}
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("order_list", OrderBase.class);

		OrderList oList = (OrderList) JSONObject.toBean(resp, OrderList.class, classMap);
		List<OrderBase> oInfos = oList.getOrder_list();
		for (int i = 0; i < oInfos.size(); i++) {
			OrderBase oInfo =oInfos.get(i);
			String imageUrl = Const.getServerUrl(request) + FileUtil.getWeChatImage(oInfo.getProduct_img(), FileUtil.CATEGORY_PRODUCT, oInfo.getProduct_id(), false);
			oInfo.setProduct_img(imageUrl);
			oInfos.set(i, oInfo);
		}
		oList.setOrder_list(oInfos);
		return JSONObject.fromObject(oList);
	}

	public JSONObject getOrder(String accessToken, String orderId) {
		String url = URL_ORDER_GET_DETAIL.replace("ACCESS_TOKEN", accessToken);

		JSONObject request = new JSONObject();
		request.put("order_id", orderId);

		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", request.toString());

		if (null == response) {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrcode("-1");
			errMsg.setErrmsg("server is busy");

			return JSONObject.fromObject(errMsg);
		}
		return response;
	}
	
	public JSONObject getOrder(HttpServletRequest request,String accessToken, String orderId){
		JSONObject resp = getOrder(accessToken, orderId);
		if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
			logger.error(resp.toString());
			return resp;
		}
		
		Order o = (Order) JSONObject.toBean(resp, Order.class);
		OrderBase oInfo =o.getOrder();
		String imageUrl = Const.getServerUrl(request) + FileUtil.getWeChatImage(oInfo.getProduct_img(), FileUtil.CATEGORY_PRODUCT, oInfo.getProduct_id(), false);
		oInfo.setProduct_img(imageUrl);
		o.setOrder(oInfo);
		return JSONObject.fromObject(o);
	}
	public int getOrderCount(JSONObject oList, String productId) {
		int count = 0;
		if (oList.containsKey("errcode") && !oList.getString("errcode").equals("0")) {
			logger.error(oList.toString());
			return 0;
		}
		JSONArray oInfos = oList.getJSONArray("order_list");
		for (int i = 0; i < oInfos.size(); i++) {
			if (productId.equals(oInfos.getJSONObject(i).getString("product_id"))) {
				count++;
			}
		}

		return count;
	}
}
