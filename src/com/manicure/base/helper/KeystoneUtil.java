/**
 * 
 */
package com.manicure.base.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manicure.keystone.service.impl.CoreService;

/**
 * @author Barrie
 *
 */
public class KeystoneUtil {
	private static final Logger logger = LoggerFactory.getLogger(KeystoneUtil.class);

	/**
	 * @return the accessToken
	 */
	public static String getAccessToken() {
		JSONObject at = getRemoteAccessToken();
		if (at.containsKey("access_token")) {
			return at.getString("access_token");
		} else {
			return null;
		}
	}

	public static String getErrmsg() {
		JSONObject at = getRemoteAccessToken();
		if (at.containsKey("errmsg")) {
			return at.getString("errmsg");
		} else {
			return null;
		}
	}

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */

	public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	public static String getTradeNo(String mchId) {
		String order = mchId + new SimpleDateFormat("yyyyMMdd").format(new Date());
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			order += r.nextInt(9);
		}
		return order;
	}

	/**
	 * 随机16为数值
	 * 
	 * @return
	 */
	public static String getNonceStr() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String currTime = outFormat.format(now);
		String strTime = currTime.substring(8, currTime.length());
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < 4; i++) {
			num = num * 10;
		}
		return (int) ((random * num)) + strTime;
	}

	public static String createSign(Map<String, Object> map, String mchKey) {
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		for (Map.Entry<String, Object> m : map.entrySet()) {
			packageParams.put(m.getKey(), m.getValue().toString());
		}

		StringBuffer sb = new StringBuffer();
		Set<?> es = packageParams.entrySet();
		Iterator<?> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!StringUtils.isEmpty(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + mchKey);

		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		return sign;
	}

	public static void accessTokenKeeper() {
		JSONObject at = refreshRemoteAccessToken();
		if (null == at) {
			logger.error("fail to refresh");
			return;
		} else if (at.containsKey("errcode")) {
			logger.error(at.toString());
			return;
		}
		
		logger.info("access token: " + at.getString("access_token"));
	}

	public static synchronized JSONObject getLocalAccessToken() {
		String at = ConfigUtil.getProperty("token.properties", "token.api.accessToken");
		String et = ConfigUtil.getProperty("token.properties", "token.api.expireTime");
		if (null == at || null == et) {
			return refreshLocalAccessToken();
		} else {
			JSONObject rst = new JSONObject();
			rst.put("access_token", at);
			rst.put("expires_in", et);
			return rst;
		}

	}

	public static JSONObject getRemoteAccessToken() {
		String url = Const.MERCHANT_DOMAIN + "/api/keystone/token/query";
		String resp = HttpClientUtil.doGet(url, null, "UTF-8");
		if (null == resp) {
			logger.error("fail to post");
			return null;
		}
		return JSONObject.fromObject(resp);
	}

	public static synchronized JSONObject refreshLocalAccessToken() {
		CoreService coreService = new CoreService();
		JSONObject at = coreService.getAccessToken(Const.APP_ID, Const.APP_SECRET);
		if (at.containsKey("errcode")) {
			logger.error(at.toString());
			return at;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("token.api.accessToken", at.getString("access_token"));
		map.put("token.api.expireTime", at.getString("expires_in"));
		map.put("token.api.timestamp", Long.toString(new Date().getTime()));
		ConfigUtil.setProperty("token.properties", map);
		return at;
	}

	public static JSONObject refreshRemoteAccessToken() {
		String url = Const.MERCHANT_DOMAIN + "/api/keystone/token/refresh";
		String resp = HttpClientUtil.doGet(url, null, "UTF-8");
		if (null == resp) {
			logger.error("fail to post");
			return null;
		}
		return JSONObject.fromObject(resp);
	}

}
