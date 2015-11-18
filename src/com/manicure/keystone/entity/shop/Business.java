/**
 * 
 */
package com.manicure.keystone.entity.shop;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class Business extends BaseEntity {
	private BaseInfo base_info;

	/**
	 * @return the base_info
	 */
	public BaseInfo getBase_info() {
		return base_info;
	}

	/**
	 * @param base_info
	 *            the base_info to set
	 */
	public void setBase_info(BaseInfo base_info) {
		this.base_info = base_info;
	}

}
