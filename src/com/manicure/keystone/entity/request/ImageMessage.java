/**
 * 
 */
package com.manicure.keystone.entity.request;

/**
 * 图片消息
 * 
 * @author Barrie
 *
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String PicUrl;

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return PicUrl;
	}

	/**
	 * @param picUrl
	 *            the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

}
