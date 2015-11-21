/**
 *
 */
package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.FileUtil;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.entity.shop.*;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IShopService;
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
public class ShopService extends BaseService implements IShopService {
    @Resource
    ICoreService coreService;

    /**
     * @throws ConnectionFailedException
     */
    public JSONObject getShopList(String accessToken, String begin, String limit) throws ConnectionFailedException {
        String url = Const.PublicPlatform.URL_SHOP_GET_LIST.replace("TOKEN", accessToken);
        JSONObject request = new JSONObject();
        request.put("begin", begin);
        request.put("limit", limit);

        JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", request.toString());

        if (null == response) {
            throw new ConnectionFailedException();
        }

        return response;
    }

    public JSONObject getShopList(HttpServletRequest request, String accessToken, String begin, String limit) throws ConnectionFailedException {
        JSONObject resp = getShopList(accessToken, begin, limit);
        if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
            logger.error(resp.toString());
            return resp;
        }
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("business_list", Business.class);
        classMap.put("photo_list", PhotoUrl.class);
        ShopList sList = (ShopList) JSONObject.toBean(resp, ShopList.class, classMap);
        List<Business> sBizs = sList.getBusiness_list();
        for (int i = 0; i < sBizs.size(); i++) {
            Business sBiz = sBizs.get(i);
            BaseInfo sInfo = sBiz.getBase_info();
            List<PhotoUrl> imgs = sInfo.getPhoto_list();
            for (int j = 0; j < imgs.size(); j++) {
                PhotoUrl photourl = imgs.get(j);
                String img = Const.getServerUrl(request) + FileUtil.getWeChatImage(photourl.getPhoto_url(), FileUtil.CATEGORY_SHOP, sInfo.getPoi_id() + "-" + j, false);
                photourl.setPhoto_url(img);
                imgs.set(j, photourl);
            }
            sInfo.setPhoto_list(imgs);
            sBiz.setBase_info(sInfo);
            sBizs.set(i, sBiz);
        }

        sList.setBusiness_list(sBizs);
        return JSONObject.fromObject(sList);
    }

    /**
     * @throws ConnectionFailedException
     */
    public JSONObject getShop(String accessToken, String poi_id) throws ConnectionFailedException {
        String url = Const.PublicPlatform.URL_SHOP_GET_DETAIL.replace("TOKEN", accessToken);

        JSONObject request = new JSONObject();
        request.put("poi_id", poi_id);

        JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", request.toString());

        if (null == response) {
            throw new ConnectionFailedException();
        }
        return response;
    }

    public JSONObject getShop(HttpServletRequest request, String accessToken, String poi_id) throws ConnectionFailedException {
        JSONObject resp = getShop(accessToken, poi_id);
        if (resp.containsKey("errcode") && !resp.getString("errcode").equals("0")) {
            logger.error(resp.toString());
            return resp;
        }
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("photo_list", PhotoUrl.class);
        Shop s = (Shop) JSONObject.toBean(resp, Shop.class, classMap);
        Business sBiz = s.getBusiness();
        BaseInfo sInfo = sBiz.getBase_info();
        List<PhotoUrl> imgs = sInfo.getPhoto_list();
        for (int j = 0; j < imgs.size(); j++) {
            PhotoUrl photourl = imgs.get(j);
            String img = Const.getServerUrl(request) + FileUtil.getWeChatImage(photourl.getPhoto_url(), FileUtil.CATEGORY_SHOP, sInfo.getPoi_id() + "-" + j, false);
            photourl.setPhoto_url(img);
            imgs.set(j, photourl);
        }
        sInfo.setPhoto_list(imgs);
        sBiz.setBase_info(sInfo);
        s.setBusiness(sBiz);


        return JSONObject.fromObject(s);
    }

}
