/**
 *
 */
package com.manicure.base.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author xieky.fnst
 *
 */
public class FileUtil {
	public static String PATH_IMAGE = "/images";
	public static String CATEGORY_MATERIAL = "/material";
	public static String CATEGORY_PRODUCT = "/product";
	public static String CATEGORY_SHOP = "/shop";
	public static String CATEGORY_USER = "/user";

	private static String getFile(String url, String subPath, String category, String filename, String extension, boolean force) {
		String localPathStr = Const.getServerPath() + "/pages/mobile/static" + subPath + category;
		File localPath = new File(localPathStr);
		if (!localPath.exists() && !localPath.isDirectory()) {
			localPath.mkdirs();
		}
		String savedFileName = filename + "." + extension;
		try {
			File file = new File(localPathStr + "/" + savedFileName);
			if (force) {
				file.delete();
			}
			if (!file.exists()) {
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet getMethod = new HttpGet(url);

				HttpResponse response = httpClient.execute(getMethod);

				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == HttpStatus.SC_OK) {

					File storeFile = new File(localPathStr + "/" + savedFileName + ".tmp"); // 先存为临时文件，等全部下完再改回原来的文件名

					FileOutputStream outputStream = new FileOutputStream(storeFile);
					InputStream inputStream = response.getEntity().getContent();
					byte b[] = new byte[32 * 1024];
					int j = 0;

					while ((j = inputStream.read(b)) != -1) {
						outputStream.write(b, 0, j);
					}

					if (outputStream != null) {
						outputStream.close();
					}

					storeFile.renameTo(new File(localPathStr + "/" + savedFileName));
					b = null;
				} else {

					return null;
				}
				response = null;
				getMethod.releaseConnection();
			}
		} catch (Exception e) {
			return null;
		}

		return ("/mobile/static" + subPath + category + "/" + savedFileName);
	}

	public static String getWeChatImage(String url, String category, String filename, boolean force) {
		URL urlObj;
		try {
			urlObj = new URL(url);
		} catch (MalformedURLException e) {
			return "";
		}
		String[] aUrl = urlObj.getQuery().split("=");

		return getFile(url, PATH_IMAGE, category, filename, aUrl[aUrl.length - 1], force);
	}

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub
		URL urlObj = new URL("http://mmbiz.qpic.cn/mmbiz/1icdbWnlG9Lkl3jMlLwibfPdwmP1H9jGJpq0m7b8PAOMSG7zjiaEOwg3fOAriaOCTs0hMOJEsB5GsH5M0E3YWco3Qg/0?wx_fmt=jpeg");
		String[] aUrl = urlObj.getQuery().split("=");

		System.out.println(aUrl[aUrl.length - 1]);
	}

}