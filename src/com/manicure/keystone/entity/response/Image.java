/**
 * 
 */
package com.manicure.keystone.entity.response;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class Image extends BaseEntity {
	private String MediaId;

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

}
