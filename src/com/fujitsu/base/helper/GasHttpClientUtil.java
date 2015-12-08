/**
 * 
 */
package com.fujitsu.base.helper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.constants.Const.gasApi;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.client.entity.WebSocketResponseMessage;

import net.sf.json.JSONObject;

/**
 * @author VM
 *
 */
public class GasHttpClientUtil extends HttpClientUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(GasHttpClientUtil.class);
	
	/**
	 * 针对openId生成token后再请求
	 * @param url
	 * @param params
	 * @param charset
	 * @param openID
	 * @return
	 * @throws ConnectionFailedException
	 * @throws UnsupportedEncodingException 
	 */
	 public static String post(String url, Map<String, String> params, String charset,String openID) throws ConnectionFailedException, UnsupportedEncodingException {
		 List<NameValuePair> tokenPair = new ArrayList<NameValuePair>();
		 tokenPair.add(new BasicNameValuePair("authorizeType",gasApi.AUTHORIZETYPE));
		 tokenPair.add(new BasicNameValuePair("authorizeID",openID));
		 String tokenResp = doPost(Const.gasApi.URL+"/service/ccstWeChatgetToken.htm", new UrlEncodedFormEntity(tokenPair, charset),charset);
		 logger.info("ccstWeChatgetToken: " + tokenResp);
	     // 防止重复获得token 
        WebSocketResponseMessage messageObject = new WebSocketResponseMessage();
		messageObject = (WebSocketResponseMessage)JSONObject.toBean(JSONObject.fromObject(tokenResp),WebSocketResponseMessage.class);
		if (0!= messageObject.getErrorCode()) {
			return String.valueOf(messageObject.getErrorCode());
		} else {
			params.put("token", messageObject.getResult().getToken());
		}
		UrlEncodedFormEntity formEntity = null;
        try {
            if (null != params) {
                List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                    valuePairs.add(nameValuePair);
                }
                formEntity = new UrlEncodedFormEntity(valuePairs, charset);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("参数转码有误",e);
        }
	    return doPost(url, formEntity, charset);
	  }
	 
}
