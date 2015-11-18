/**
 * 
 */
package com.manicure.keystone.entity.product;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class Express extends BaseEntity {
	private String id;
	private int price;

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
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

}
