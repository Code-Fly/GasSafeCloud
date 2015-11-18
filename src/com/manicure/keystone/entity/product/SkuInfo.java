/**
 * 
 */
package com.manicure.keystone.entity.product;

import java.util.List;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class SkuInfo extends BaseEntity {
	private String id;
	private List<String> vid;

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
	public List<String> getVid() {
		return vid;
	}

	/**
	 * @param vid
	 *            the vid to set
	 */
	public void setVid(List<String> vid) {
		this.vid = vid;
	}

}
