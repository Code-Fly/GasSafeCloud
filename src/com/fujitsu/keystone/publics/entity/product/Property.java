/**
 * 
 */
package com.fujitsu.keystone.publics.entity.product;

import com.fujitsu.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class Property extends BaseEntity {
	private String id;
	private String vid;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the vid
	 */
	public String getVid() {
		return vid;
	}

	/**
	 * @param vid
	 *            the vid to set
	 */
	public void setVid(String vid) {
		this.vid = vid;
	}

}
