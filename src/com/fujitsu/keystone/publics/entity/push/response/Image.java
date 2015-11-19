/**
 * 
 */
package com.fujitsu.keystone.publics.entity.push.response;

import com.fujitsu.base.entity.BaseEntity;

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
