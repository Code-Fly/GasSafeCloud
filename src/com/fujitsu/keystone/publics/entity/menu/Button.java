/**
 * 
 */
package com.fujitsu.keystone.publics.entity.menu;

import com.fujitsu.base.entity.BaseEntity;

/**
 * 按钮的基类
 * 
 * @author Barrie
 *
 */
public class Button extends BaseEntity {
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
