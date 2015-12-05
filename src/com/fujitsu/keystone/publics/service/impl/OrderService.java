/**
 *
 */
package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.FileUtil;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.entity.order.Order;
import com.fujitsu.keystone.publics.entity.order.OrderBase;
import com.fujitsu.keystone.publics.entity.order.OrderList;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IOrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Barrie
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

    public JSONObject getOrderList(String accessToken, String status, String beginTime, String endTime) throws ConnectionFailedException {
        String url = Const.PublicPlatform.URL_ORDER_GET_LIST.replace("ACCESS_TOKEN", accessToken);
        JSONObject request = new JSONObject();
        String response = null;

        if (!"0".equals(status)) {
            request.put("status", status);
        }
        if (!"0".equals(beginTime)) {
            request.put("begintime", beginTime);
        }

        if (!"0".equals(endTime)) {
            request.put("endtime", endTime);
        }
        if (0 == request.size()) {
            response = HttpClientUtil.doGet(url, "UTF-8");
        } else {
            response = HttpClientUtil.doGet(url, request.toString(), "UTF-8");
        }


        return JSONObject.fromObject(response);
    }

    public JSONObject getOrderList(HttpServletRequest request, String accessToken, String status, String beginTime, String endTime) throws ConnectionFailedException {
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
            OrderBase oInfo = oInfos.get(i);
            String imageUrl = Const.getServerUrl(request) + FileUtil.getWeChatImage(oInfo.getProduct_img(), FileUtil.CATEGORY_PRODUCT, oInfo.getProduct_id(), false);
            oInfo.setProduct_img(imageUrl);
            oInfos.set(i, oInfo);
        }
        oList.setOrder_list(oInfos);
        return JSONObject.fromObject(oList);
    }

    public JSONObject getOrder(String accessToken, String orderId) throws ConnectionFailedException {
        String url = Const.PublicPlatform.URL_ORDER_GET_DETAIL.replace("ACCESS_TOKEN", accessToken);

        JSONObject request = new JSONObject();
        request.put("order_id", orderId);

        String response = HttpClientUtil.doPost(url, request.toString(), "UTF-8");

        return JSONObject.fromObject(response);
    }

    public JSONObject getOrder(HttpServletRequest request, String accessToken, String orderId) throws ConnectionFailedException {
        JSONObject resp = getOrder(accessToken, orderId);
        if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
            logger.error(resp.toString());
            return resp;
        }

        Order o = (Order) JSONObject.toBean(resp, Order.class);
        OrderBase oInfo = o.getOrder();
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
