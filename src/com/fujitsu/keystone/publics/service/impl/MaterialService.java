/**
 * 
 */
package com.fujitsu.keystone.publics.service.impl;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IMaterialService;

/**
 * @author Barrie
 *
 */
@Service
public class MaterialService extends BaseService implements IMaterialService {
	@Resource
	ICoreService coreService;

	public static String MATERIAL_TYPE_IMAGE = "image";
	public static String MATERIAL_TYPE_VIDEO = "video";
	public static String MATERIAL_TYPE_VOICE = "voice";
	public static String MATERIAL_TYPE_NEWS = "news";

	/**
	 * @throws ConnectionFailedException 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * 
	 */
	public JSONObject getMaterialList(String accessToken, String type, int offset, int count) throws ConnectionFailedException {

		String url = URL_MATERIAL_GET_LIST.replace("ACCESS_TOKEN", accessToken);

		JSONObject request = new JSONObject();
		request.put("type", type);
		request.put("offset", offset);
		request.put("count", count);

		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", request.toString());

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
	}

	/**
	 * 
	 * @param accessToken
	 * @param mediaId
	 * @return
	 * @throws ConnectionFailedException 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public JSONObject getMaterial(String accessToken, String mediaId) throws ConnectionFailedException {

		String url = URL_MATERIAL_GET_DETAIL.replace("ACCESS_TOKEN", accessToken);

		JSONObject request = new JSONObject();
		request.put("media_id", mediaId);

		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", request.toString());

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
	}
}