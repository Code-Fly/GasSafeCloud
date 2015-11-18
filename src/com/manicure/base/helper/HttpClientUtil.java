/**
 * 
 */
package com.manicure.base.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Barrie
 *
 */
public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static String doPost(String url, Map<String, String> params, String charset) {
		try {
			HttpPost httpPost = new HttpPost(url);
			HttpClient client = new DefaultHttpClient();
			if (null != params) {
				List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
					valuePairs.add(nameValuePair);
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, charset);
				httpPost.setEntity(formEntity);
			}
			HttpResponse resp = client.execute(httpPost);

			HttpEntity entity = resp.getEntity();
			String respContent = EntityUtils.toString(entity, charset).trim();
			httpPost.abort();
			client.getConnectionManager().shutdown();

			return respContent;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String doPostJson(String url, String jsonStr, String charset) {
		try {
			HttpPost httpPost = new HttpPost(url);
			HttpClient client = new DefaultHttpClient();
			StringEntity reqEntity = new StringEntity(jsonStr);
			reqEntity.setContentType("application/json; charset=utf-8");
			httpPost.setEntity(reqEntity);
			HttpResponse resp = client.execute(httpPost);

			HttpEntity entity = resp.getEntity();
			String respContent = EntityUtils.toString(entity, charset).trim();
			httpPost.abort();
			client.getConnectionManager().shutdown();

			return respContent;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String doGet(String url, Map<String, String> params, String charset) {
		try {
			HttpGet httpGet = null;
			HttpClient client = new DefaultHttpClient();
			if (null != params) {
				List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
					valuePairs.add(nameValuePair);
				}
				String param = URLEncodedUtils.format(valuePairs, charset);
				httpGet = new HttpGet(url + "?" + param);

			} else {
				httpGet = new HttpGet(url);
			}

			HttpResponse resp = client.execute(httpGet);

			HttpEntity entity = resp.getEntity();
			String respContent = EntityUtils.toString(entity, charset).trim();
			httpGet.abort();
			client.getConnectionManager().shutdown();

			return respContent;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String doHttpsPost(String url, String charset) {
		KeyStore keyStore = null;
		FileInputStream instream = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			// P12文件目录
			instream = new FileInputStream(new File(Const.MCH_KEYSTONE));
			keyStore.load(instream, Const.MCH_KEYSTONE_SECRET.toCharArray());// 这里写密码..默认是你的MCHID
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, Const.MCH_KEYSTONE_SECRET.toCharArray())// 这里也是写密码的
					.build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost httpPost = new HttpPost(url); // 设置响应头信息
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			// 微信返回的报文时GBK，直接使用httpcore解析乱码
			String respStr = EntityUtils.toString(response.getEntity(), charset);
			EntityUtils.consume(entity);
			return respStr;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

	}

	public static String doHttpsPost(String url, Map<String, String> params, String charset) {
		KeyStore keyStore = null;
		FileInputStream instream = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			// P12文件目录
			instream = new FileInputStream(new File(Const.MCH_KEYSTONE));
			keyStore.load(instream, Const.MCH_KEYSTONE_SECRET.toCharArray());// 这里写密码..默认是你的MCHID
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, Const.MCH_KEYSTONE_SECRET.toCharArray())// 这里也是写密码的
					.build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost httpPost = new HttpPost(url); // 设置响应头信息
			if (null != params) {
				List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
					valuePairs.add(nameValuePair);
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, charset);
				httpPost.setEntity(formEntity);
			}

			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			// 微信返回的报文时GBK，直接使用httpcore解析乱码
			String respStr = EntityUtils.toString(response.getEntity(), charset);
			EntityUtils.consume(entity);
			return respStr;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

	}

	public static String doHttpsPost(String url, String params, String charset) {
		KeyStore keyStore = null;
		FileInputStream instream = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			// P12文件目录
			// InputStream instream =
			// TestUtil.class.getResourceAsStream(KEYSTORE_FILE);
			instream = new FileInputStream(new File(Const.MCH_KEYSTONE));
			keyStore.load(instream, Const.MCH_KEYSTONE_SECRET.toCharArray());// 这里写密码..默认是你的MCHID
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, Const.MCH_KEYSTONE_SECRET.toCharArray())// 这里也是写密码的
					.build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost httpPost = new HttpPost(url); // 设置响应头信息
			httpPost.setEntity(new StringEntity(params, "UTF-8"));
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			// String jsonStr = toStringInfo(response.getEntity(), "UTF-8");

			// 微信返回的报文时GBK，直接使用httpcore解析乱码
			String respStr = EntityUtils.toString(response.getEntity(), charset);
			EntityUtils.consume(entity);
			return respStr;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			try {
				instream.close();
				response.close();
				httpclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
				return null;
			}
		}

	}

	public static String doHttpsGet(String url, Map<String, String> params, String charset) {
		KeyStore keyStore;
		FileInputStream instream = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			instream = new FileInputStream(new File("/Users/Barrie/apiclient_cert.p12"));
			keyStore.load(instream, "1252726101".toCharArray());
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "1252726101".toCharArray()).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpGet httpGet = null;
			if (null != params) {
				List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
					valuePairs.add(nameValuePair);
				}
				String param = URLEncodedUtils.format(valuePairs, charset);
				logger.info(url + "&" + param);
				httpGet = new HttpGet(url + "&" + param);

			} else {
				httpGet = new HttpGet(url);
			}

			HttpResponse resp = client.execute(httpGet);

			HttpEntity entity = resp.getEntity();
			String respContent = EntityUtils.toString(entity, charset).trim();
			httpGet.abort();
			client.close();

			return respContent;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	public static JSONObject doHttpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			for (int i = 0; i < 2; i++) {
				// 创建SSLContext对象，并使用我们指定的信任管理器初始化
				TrustManager[] tm = { new CustomX509TrustManager() };
				SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
				// 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();

				URL url = new URL(requestUrl);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setSSLSocketFactory(ssf);

				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
				// 设置请求方式（GET/POST）
				conn.setRequestMethod(requestMethod);

				// 当outputStr不为null时向输出流写数据
				if (null != outputStr) {
					OutputStream outputStream = conn.getOutputStream();
					// 注意编码格式
					outputStream.write(outputStr.getBytes("UTF-8"));
					outputStream.close();
				}

				// 从输入流读取返回内容
				InputStream inputStream = conn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String str = null;
				StringBuffer buffer = new StringBuffer();
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}

				// 释放资源
				bufferedReader.close();
				inputStreamReader.close();
				inputStream.close();
				inputStream = null;
				conn.disconnect();
				jsonObject = JSONObject.fromObject(buffer.toString());
				if (jsonObject.containsKey("errcode") && jsonObject.getString("errcode").equals("40001")) {
					KeystoneUtil.refreshRemoteAccessToken();
				} else {
					break;
				}
			}
		} catch (ConnectException ce) {
			// logger.error("连接超时：{}", ce);
		} catch (Exception e) {
			// logger.error("https请求异常：{}", e);
		}
		return jsonObject;
	}

	private static String toJson(HttpEntity entity, String defaultCharset) throws Exception, IOException {
		final InputStream instream = entity.getContent();
		if (instream == null) {
			return null;
		}
		try {
			Args.check(entity.getContentLength() <= Integer.MAX_VALUE, "HTTP entity too large to be buffered in memory");
			int i = (int) entity.getContentLength();
			if (i < 0) {
				i = 4096;
			}
			Charset charset = null;

			if (charset == null) {
				charset = Charset.forName(defaultCharset);
			}
			if (charset == null) {
				charset = HTTP.DEF_CONTENT_CHARSET;
			}
			final Reader reader = new InputStreamReader(instream, charset);
			final CharArrayBuffer buffer = new CharArrayBuffer(i);
			final char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
			return buffer.toString();
		} finally {
			instream.close();
		}
	}

	public static void main(String[] args) {

	}
}
