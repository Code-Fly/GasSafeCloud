/**
 *
 */
package com.fujitsu.base.helper;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.constants.Const.gasApi;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.GasSafeException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.client.entity.WebSocketResponseMessage;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VM
 */
public class GasHttpClientUtil extends HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(GasHttpClientUtil.class);

    /**
     * 针对openId生成token后再请求
     *
     * @param url
     * @param params
     * @param charset
     * @param openID
     * @return
     * @throws ConnectionFailedException
     * @throws UnsupportedEncodingException
     */
    public static String gasPost(String url, Map<String, String> params, String charset, String openID) throws ConnectionFailedException, UnsupportedEncodingException {
    	return  gasPost(url, params,  charset, openID,null); 
    }
    /**
     * 针对openId生成token后再请求
     *
     * @param url
     * @param params
     * @param charset
     * @param openID
     * @return
     * @throws ConnectionFailedException
     * @throws UnsupportedEncodingException
     */
    public static String gasPost(String url, Map<String, String> params, String charset, String openID,String codeTxt) throws ConnectionFailedException, UnsupportedEncodingException {
    	Map<String, String> paramsToken = new HashMap<>();
        paramsToken.put("authorizeType", gasApi.AUTHORIZETYPE);
        paramsToken.put("authorizeID",openID);
      
         String tokenResp = doPost(Const.gasApi.URL + "ccstWeChatgetToken.htm", paramsToken, charset);
         logger.info("ccstWeChatgetToken: " + tokenResp);
         // 防止重复获得token
         WebSocketResponseMessage messageObject = new WebSocketResponseMessage();
         messageObject = (WebSocketResponseMessage) JSONObject.toBean(JSONObject.fromObject(tokenResp), WebSocketResponseMessage.class);
         if (0 != messageObject.getErrorCode()) {
             return String.valueOf(messageObject.getErrorCode());
         } else {
             params.put("token", messageObject.getResult());
         }
//         Map<String,String> logParams = new HashMap<>();
//         logParams.put("openId", openID);
//         logParams.put("token", messageObject.getResult());
//         logParams.put("sendLng", null);
//         logParams.put("sendLat", null);
//         logParams.put("opType", url.split("[.]")[0]);
//         logParams.put("codeType", gasApi.AUTHORIZETYPE);
//         logParams.put("codeTxt",codeTxt);
//         logParams.put("userid", null);
//         doPost(Const.gasApi.URL + "ccstWeChatFansOPLog.htm", logParams, charset);
         return doPost(Const.gasApi.URL + url, params, charset);
    }
    public static String doPost(String url, Map<String, String> params, String charset) throws ConnectionFailedException {
        StringEntity stringEntity = null;

        if (null != params && !params.isEmpty()) {
            List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                valuePairs.add(nameValuePair);
            }
            String queryStr = URLEncodedUtils.format(valuePairs, charset);
            queryStr = UrlUtil.decode(queryStr, charset);
            queryStr = "data=" + UrlUtil.encode(queryStr, charset);
            stringEntity = new StringEntity(queryStr, charset);
            stringEntity.setContentType("application/x-www-form-urlencoded");
        }

        return doPost(url, stringEntity, charset);
    }


    public static boolean isValid(String respStr) throws ConnectionFailedException, WeChatException, GasSafeException {
        JSONObject jsonObject = JSONObject.fromObject(respStr);
        if (jsonObject.containsKey("errorCode") && !jsonObject.getString("errorCode").equals("0")) {
            return false;
        }
        return true;
    }

}
