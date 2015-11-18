/**
 * 
 */
package com.manicure.keystone.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.manicure.base.helper.Const;
import com.manicure.base.helper.FileUtil;
import com.manicure.base.helper.HttpClientUtil;
import com.manicure.base.service.BaseService;
import com.manicure.keystone.entity.error.ErrorMsg;
import com.manicure.keystone.entity.product.Product;
import com.manicure.keystone.entity.product.ProductBase;
import com.manicure.keystone.entity.product.ProductInfo;
import com.manicure.keystone.entity.product.ProductList;
import com.manicure.keystone.entity.product.SkuList;
import com.manicure.keystone.service.iface.IProductService;

/**
 * @author Barrie
 *
 */
@Service
public class ProductService extends BaseService implements IProductService {
	@Resource
	CoreService coreService;
	@Resource
	OrderService orderService;

	public final int STATUS_ALL = 0;
	public final int STATUS_ON_SHELVES = 1;
	public final int STATUS_OFF_SHELVES = 2;

	/**
	 * 
	 */
	public JSONObject getProductList(String accessToken, int status) {
		String url = URL_PROGUCT_GET_LIST.replace("ACCESS_TOKEN", accessToken);

		JSONObject request = new JSONObject();
		request.put("status", status);
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", request.toString());

		if (null == response) {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrcode("-1");
			errMsg.setErrmsg("server is busy");

			return JSONObject.fromObject(errMsg);
		}
		return response;
	}

	public JSONObject getProduct(String accessToken, String productId) {
		String url = URL_PROGUCT_GET_DETAIL.replace("ACCESS_TOKEN", accessToken);

		JSONObject request = new JSONObject();
		request.put("product_id", productId);

		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", request.toString());

		if (null == response) {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrcode("-1");
			errMsg.setErrmsg("server is busy");

			return JSONObject.fromObject(errMsg);
		}

		return response;
	}

	public JSONObject getProductGroupList(String accessToken) {
		String url = URL_PROGUCT_GROUP_GET_LIST.replace("ACCESS_TOKEN", accessToken);

		JSONObject response = HttpClientUtil.doHttpsRequest(url, "GET", null);

		if (null == response) {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrcode("-1");
			errMsg.setErrmsg("server is busy");

			return JSONObject.fromObject(errMsg);
		}

		return response;
	}

	public JSONObject getProductGroupDetail(String accessToken, String groupId) {
		String url = URL_PROGUCT_GROUP_GET_DETAIL.replace("ACCESS_TOKEN", accessToken);

		JSONObject request = new JSONObject();
		request.put("group_id", groupId);
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", request.toString());

		if (null == response) {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrcode("-1");
			errMsg.setErrmsg("server is busy");

			return JSONObject.fromObject(errMsg);
		}

		return response;
	}

	/**
	 * formated product
	 * 
	 * @param request
	 * @param accessToken
	 * @param groupId
	 * @return
	 */
	public JSONObject getProduct(HttpServletRequest request, String accessToken, String productId) {
		JSONObject oList = orderService.getOrderList(accessToken, "0", "0", "0");

		JSONObject respProduct = getProduct(accessToken, productId);
		if (respProduct.containsKey("errcode") && !respProduct.getString("errcode").equals("0")) {
			logger.error(respProduct.toString());
			return respProduct;
		}
		Product p = (Product) JSONObject.toBean(respProduct, Product.class);
		ProductInfo pInfo = p.getProduct_info();
		ProductBase pBase = pInfo.getProduct_base();
		String imageUrl = Const.getServerUrl(request) + FileUtil.getWeChatImage(pBase.getMain_img(), FileUtil.CATEGORY_PRODUCT, pInfo.getProduct_id(), false);
		pBase.setMain_img(imageUrl);
		List<String> detail = new ArrayList<String>();
		detail.add(Integer.toString(orderService.getOrderCount(oList, pInfo.getProduct_id())));
		pBase.setDetail(detail);
		pInfo.setProduct_base(pBase);
		p.setProduct_info(pInfo);
		return JSONObject.fromObject(p);
	}

	/**
	 * get formated list
	 * 
	 * @param request
	 * @param accessToken
	 * @param status
	 * @param groupId
	 * @param orderBy
	 * @param sort
	 * @param filter
	 * @return
	 */
	public JSONObject getProductList(HttpServletRequest request, String accessToken, int status, Map<String, String> filter) {
		ProductList pList = new ProductList();
		String groupId = filter.get("groupId");
		if ("0".equals(groupId)) {
			JSONObject resp = getProductList(accessToken, status);
			if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
				logger.error(resp.toString());
				return resp;
			}

			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("products_info", ProductInfo.class);
			classMap.put("sku_list", SkuList.class);
			pList = (ProductList) JSONObject.toBean(resp, ProductList.class, classMap);
			List<ProductInfo> pInfos = pList.getProducts_info();
			JSONObject oList = orderService.getOrderList(accessToken, "0", "0", "0");

			for (int i = 0; i < pInfos.size(); i++) {
				ProductInfo pInfo = pInfos.get(i);
				ProductBase pBase = pInfo.getProduct_base();
				String imageUrl = Const.getServerUrl(request) + FileUtil.getWeChatImage(pBase.getMain_img(), FileUtil.CATEGORY_PRODUCT, pInfo.getProduct_id(), false);
				pBase.setMain_img(imageUrl);
				List<String> detail = new ArrayList<String>();
				detail.add(Integer.toString(orderService.getOrderCount(oList, pInfo.getProduct_id())));
				pBase.setDetail(detail);
				pInfo.setProduct_base(pBase);
				pInfos.set(i, pInfo);
			}
			pList.setProducts_info(pInfos);
		} else {
			JSONObject respGroupDetail = getProductGroupDetail(accessToken, groupId);
			if (respGroupDetail.containsKey("errcode") && !respGroupDetail.getString("errcode").equals("0")) {
				logger.error(respGroupDetail.toString());
				return respGroupDetail;
			}
			List<String> pIds = respGroupDetail.getJSONObject("group_detail").getJSONArray("product_list");
			List<ProductInfo> pInfos = new ArrayList<ProductInfo>();
			JSONObject oList = orderService.getOrderList(accessToken, "0", "0", "0");

			for (int i = 0; i < pIds.size(); i++) {
				JSONObject respProduct = getProduct(accessToken, pIds.get(i));
				if (respProduct.containsKey("errcode") && !respProduct.getString("errcode").equals("0")) {
					logger.error(respProduct.toString());
					return respProduct;
				}
				Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("sku_list", SkuList.class);
				Product p = (Product) JSONObject.toBean(respProduct, Product.class, classMap);
				ProductInfo pInfo = p.getProduct_info();
				if (status != pInfo.getStatus()) {
					continue;
				}
				ProductBase pBase = pInfo.getProduct_base();
				String imageUrl = Const.getServerUrl(request) + FileUtil.getWeChatImage(pBase.getMain_img(), FileUtil.CATEGORY_PRODUCT, pInfo.getProduct_id(), false);
				pBase.setMain_img(imageUrl);
				List<String> detail = new ArrayList<String>();
				detail.add(Integer.toString(orderService.getOrderCount(oList, pInfo.getProduct_id())));
				pBase.setDetail(detail);
				pInfo.setProduct_base(pBase);
				pInfos.add(pInfo);

			}
			pList.setProducts_info(pInfos);
		}

		pList.sort(filter);

		return JSONObject.fromObject(pList);
	}
}
