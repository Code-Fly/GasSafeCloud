/**
 * 
 */
package com.manicure.keystone.entity.request;

/**
 * 音频消息
 * 
 * @author Barrie
 *
 */
public class VoiceMessage {
	// 媒体ID
	private String MediaId;
	// 语音格式
	private String Format;

	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return MediaId;
	}

	/**
	 * @param mediaId
	 *            the mediaId to set
	 */
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return Format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format) {
		Format = format;
	}

}
