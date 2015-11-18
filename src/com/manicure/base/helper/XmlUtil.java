/**
 * 
 */
package com.manicure.base.helper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @author Barrie
 *
 */
public class XmlUtil {

	private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return Map<String, String>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return Map<String, String>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String str) {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		try {
			// 从request中取得输入流
			InputStream inputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());

			// 释放资源
			inputStream.close();
			inputStream = null;
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	/**
	 * 扩展xstream使其支持CDATA
	 */
	public static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	public static String toXMLWithCDATA(Map<String, Object> map) {
		String xml = "<xml>";
		Set<String> set = map.keySet();
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			String str = i.next();
			xml += "<" + str + ">" + "<![CDATA[" + map.get(str) + "]]>" + "</" + str + ">";
		}
		xml += "</xml>";
		return xml;
	}

	public static String toXML(Map<String, Object> map) {
		String xml = "<xml>";
		Set<String> set = map.keySet();
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			String str = i.next();
			xml += "<" + str + ">" + map.get(str) + "</" + str + ">";
		}
		xml += "</xml>";
		return xml;
	}

}
