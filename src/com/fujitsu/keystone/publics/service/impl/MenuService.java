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
import com.fujitsu.keystone.publics.service.iface.IMenuService;
import com.fujitsu.keystone.publics.service.iface.IMessageService;

/**
 * @author Barrie
 *
 */
@Service
public class MenuService extends BaseService  implements IMenuService{
		
	public static final String QP_SFCX = "QP_SFCX";
	public static final String QP_GZJL  = "QP_GZJL";
	public static final String QP_LZGZ  = "QP_LZGZ";
	public static final String QP_SMSY = "QP_SMSY";
	public static final String QP_AQDW 	 = "QP_AQDW";
	 
	public static final String GL_CZCC = "GL_CZCC";
	public static final String GL_PSYS = "GL_PSYS";
	public static final String GL_JYJC = "GL_JYJC";
	 
	public static final String FW_RSQP = "FW_RSQP";
	public static final String FW_YQAQ = "FW_YQAQ";
	public static final String FW_ZXTS = "FW_ZXTS";

	@Resource
	IMessageService messageService;

	/**
	 * 
	 * @param accessToken
	 * @param json
	 * @return
	 * @throws ConnectionFailedException 
	 */
	public JSONObject create(String accessToken, JSONObject json) throws ConnectionFailedException {

		// 拼装创建菜单的url
		String url = URL_MENU_CREATE.replace("ACCESS_TOKEN", accessToken);		
		// 调用接口创建菜单
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "POST", json.toString());

		if (null == response) {
			throw new ConnectionFailedException();			
		}
		return response;
	}

	/**
	 * 
	 * @param accessToken
	 * @return
	 * @throws ConnectionFailedException 
	 */
	public JSONObject get(String accessToken) throws ConnectionFailedException {
		String url = URL_MENU_GET.replace("ACCESS_TOKEN", accessToken);
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "GET", null);

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
	}

	/**
	 * 
	 * @param accessToken
	 * @return
	 * @throws ConnectionFailedException 
	 */
	public JSONObject delete(String accessToken) throws ConnectionFailedException {
		String url = URL_MENU_DELETE.replace("ACCESS_TOKEN", accessToken);
		JSONObject response = HttpClientUtil.doHttpsRequest(url, "GET", null);

		if (null == response) {
			throw new ConnectionFailedException();
		}
		return response;
	}

	
}
