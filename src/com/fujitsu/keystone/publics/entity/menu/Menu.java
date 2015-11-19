/**
 * 
 */
package com.fujitsu.keystone.publics.entity.menu;

import com.fujitsu.base.entity.BaseEntity;

/**
 * 菜单
 * 
 * @author Barrie
 *
 */
public class Menu extends BaseEntity {
	private Button[] button;

	/**
	 * @return the button
	 */
	public Button[] getButton() {
		return button;
	}

	/**
	 * @param button
	 *            the button to set
	 */
	public void setButton(Button[] button) {
		this.button = button;
	}
}
