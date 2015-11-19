/**
 * 
 */
package com.fujitsu.keystone.publics.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.entity.push.response.Article;
import com.fujitsu.keystone.publics.entity.push.response.ImageMessage;
import com.fujitsu.keystone.publics.entity.push.response.MusicMessage;
import com.fujitsu.keystone.publics.entity.push.response.NewsMessage;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.entity.push.response.TransferCustomerService;
import com.fujitsu.keystone.publics.entity.push.response.VideoMessage;
import com.fujitsu.keystone.publics.entity.push.response.VoiceMessage;
import com.fujitsu.keystone.publics.service.iface.IMessageService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @author Barrie
 *
 */
@Service
public class MessageService extends BaseService implements IMessageService {	

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return Map<String, String>
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject parseXml(HttpServletRequest request) throws IOException, DocumentException {
		JSONObject json = new JSONObject();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> rootElementList = root.elements();

		// 遍历所有子节点
		for (Element re : rootElementList) {

			JSONObject jsonChild = new JSONObject();
			List<Element> childElementList = re.elements();
			if (childElementList.isEmpty()){
				json.put(re.getName(), re.getText());
			}else{
				for (Element ce : childElementList) {
					jsonChild.put(ce.getName(), ce.getText());
				}
				json.put(re.getName(), jsonChild);
			}		

		}

		// 释放资源
		inputStream.close();
		inputStream = null;

		return json;
	}

	/**
	 * 扩展xstream使其支持CDATA
	 */
	private static XStream xstream = new XStream(new XppDriver() {
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

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static String messageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 图片消息对象转换成xml
	 * 
	 * @param imageMessage
	 *            图片消息对象
	 * @return xml
	 */
	public static String messageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 语音消息对象转换成xml
	 * 
	 * @param voiceMessage
	 *            语音消息对象
	 * @return xml
	 */
	public static String messageToXml(VoiceMessage voiceMessage) {
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}

	/**
	 * 视频消息对象转换成xml
	 * 
	 * @param videoMessage
	 *            视频消息对象
	 * @return xml
	 */
	public static String messageToXml(VideoMessage videoMessage) {
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage
	 *            音乐消息对象
	 * @return xml
	 */
	public static String messageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 *            图文消息对象
	 * @return xml
	 */
	public static String messageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	public static String messageToXml(TransferCustomerService transferMessage) {
		xstream.alias("xml", transferMessage.getClass());
		return xstream.toXML(transferMessage);
	}

}
