/**
 *
 */
package com.fujitsu.base.helper;

import com.fujitsu.base.exception.ConnectionFailedException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Barrie
 */
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String doGet(String url, String charset) throws ConnectionFailedException {
        return get(url, null, charset);
    }

    public static String doGet(String url, Map<String, String> params, String charset) throws ConnectionFailedException {

        List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
            valuePairs.add(nameValuePair);
        }
        String param = URLEncodedUtils.format(valuePairs, charset);

        return get(url, param, charset);
    }

    public static String doGet(String url, String params, String charset) throws ConnectionFailedException {

        return get(url, params, charset);

    }


    public static String doPost(String url, String charset) throws ConnectionFailedException {
        return post(url, null, charset);
    }

    public static String doPost(String url, Map<String, String> params, String charset) throws ConnectionFailedException {
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
            logger.error(e.getMessage());
        }
        return post(url, formEntity, charset);
    }

    public static String doPost(String url, String params, String charset) throws ConnectionFailedException {
        StringEntity stringEntity = null;
        if (null != params) {
            stringEntity = new StringEntity(params, "UTF-8");
        }
        return post(url, stringEntity, charset);

    }

    private static String post(String url, StringEntity sEntity, String charset) throws ConnectionFailedException {
        PoolingHttpClientConnectionManager connManager = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        String respStr = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new CustomX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();

            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            httpclient = HttpClients.custom().setConnectionManager(connManager).build();

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            HttpPost httpPost = new HttpPost(url); // 设置响应头信息

            if (null != sEntity) {
                httpPost.setEntity(sEntity);
            }

            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            // 微信返回的报文时GBK，直接使用httpcore解析乱码
            respStr = EntityUtils.toString(response.getEntity(), charset);
            EntityUtils.consume(entity);
            httpPost.abort();

        } catch (Exception e) {
            throw new ConnectionFailedException(e);
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return respStr;

    }

    private static String get(String url, String param, String charset) {
        PoolingHttpClientConnectionManager connManager = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        String respStr = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new CustomX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();

            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            httpclient = HttpClients.custom().setConnectionManager(connManager).build();

            HttpGet httpGet = null;
            if (null != param) {
                httpGet = new HttpGet(url + "&" + param);

            } else {
                httpGet = new HttpGet(url);
            }

            response = httpclient.execute(httpGet);

            HttpEntity entity = response.getEntity();
            respStr = EntityUtils.toString(entity, charset).trim();
            httpGet.abort();

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return respStr;
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


    public static void main(String[] args) {

    }
}
