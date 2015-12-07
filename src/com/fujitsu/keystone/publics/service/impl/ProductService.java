/**
 * 
 */
package com.fujitsu.keystone.publics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.WeChatClientUtil;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.constants.Const;
import com.fujitsu.base.helper.FileUtil;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.entity.product.Product;
import com.fujitsu.keystone.publics.entity.product.ProductBase;
import com.fujitsu.keystone.publics.entity.product.ProductInfo;
import com.fujitsu.keystone.publics.entity.product.ProductList;
import com.fujitsu.keystone.publics.entity.product.SkuList;
import com.fujitsu.keystone.publics.service.iface.IOrderService;
import com.fujitsu.keystone.publics.service.iface.IProductService;

/**
 * @author Barrie
 *
 */
@Service
public class ProductService extends BaseService implements IProductService {
	@Resource
	IOrderService orderService;

	public final int STATUS_ALL = 0;
	public final int STATUS_ON_SHELVES = 1;
	public final int STATUS_OFF_SHELVES = 2;

	/**
	 * @throws ConnectionFailedException 
	 * 
	 */
	public JSONObject getProductList(String accessToken, int status) throws ConnectionFailedException, WeChatException {
		String url = Const.PublicPlatform.URL_PRODUCT_GET_LIST.replace("ACCESS_TOKEN", accessToken);

		JSONObject request = new JSONObject();
		request.put("status", status);

		String response = WeChatClientUtil.doPost(url, request.toString(), "UTF-8");

        return JSONObject.fromObject(response);
    }

	public JSONObject getProduct(String accessToken, String productId) throws ConnectionFailedException, WeChatException {
		String url = Const.PublicPlatform.URL_PRODUCT_GET_DETAIL.replace("ACCESS_TOKEN", accessToken);

		JSONObject request = new JSONObject();
		request.put("product_id", productId);

		String response = WeChatClientUtil.doPost(url, request.toString(), "UTF-8");

        return JSONObject.fromObject(response);
    }

	public JSONObject getProductGroupList(String accessToken) throws ConnectionFailedException, WeChatException {
		String url = Const.PublicPlatform.URL_PRODUCT_GROUP_GET_LIST.replace("ACCESS_TOKEN", accessToken);

		String response = WeChatClientUtil.doGet(url, "UTF-8");

        return JSONObject.fromObject(response);
    }

	public JSONObject getProductGroupDetail(String accessToken, String groupId) throws ConnectionFailedException, WeChatException {
		String url = Const.PublicPlatform.URL_PRODUCT_GROUP_GET_DETAIL.replace("ACCESS_TOKEN", accessToken);

		JSONObject request = new JSONObject();
		request.put("group_id", groupId);

		String response = WeChatClientUtil.doPost(url, request.toString(), "UTF-8");

        return JSONObject.fromObject(response);
    }

	/**
	 *
	 * @param request
	 * @param accessToken
	 * @param productId
	 * @return
	 * @throws ConnectionFailedException
     */
	public JSONObject getProduct(HttpServletRequest request, String accessToken, String productId) throws ConnectionFailedException, WeChatException {
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
	 *
	 * @param request
	 * @param accessToken
	 * @param status
	 * @param filter
	 * @return
	 * @throws ConnectionFailedException
     */
	public JSONObject getProductList(HttpServletRequest request, String accessToken, int status, Map<String, String> filter) throws ConnectionFailedException, WeChatException {
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
