/**
 * 
 */
package com.manicure.keystone.entity.product;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class AttrExt extends BaseEntity {
	private int isPostFree;
	private int isHasReceipt;
	private int isUnderGuaranty;
	private int isSupportReplace;
	private Location location;

	/**
	 * @return the isPostFree
	 */
	public int getIsPostFree() {
		return isPostFree;
	}

	/**
	 * @param isPostFree
	 *            the isPostFree to set
	 */
	public void setIsPostFree(int isPostFree) {
		this.isPostFree = isPostFree;
	}

	/**
	 * @return the isHasReceipt
	 */
	public int getIsHasReceipt() {
		return isHasReceipt;
	}

	/**
	 * @param isHasReceipt
	 *            the isHasReceipt to set
	 */
	public void setIsHasReceipt(int isHasReceipt) {
		this.isHasReceipt = isHasReceipt;
	}

	/**
	 * @return the isUnderGuaranty
	 */
	public int getIsUnderGuaranty() {
		return isUnderGuaranty;
	}

	/**
	 * @param isUnderGuaranty
	 *            the isUnderGuaranty to set
	 */
	public void setIsUnderGuaranty(int isUnderGuaranty) {
		this.isUnderGuaranty = isUnderGuaranty;
	}

	/**
	 * @return the isSupportReplace
	 */
	public int getIsSupportReplace() {
		return isSupportReplace;
	}

	/**
	 * @param isSupportReplace
	 *            the isSupportReplace to set
	 */
	public void setIsSupportReplace(int isSupportReplace) {
		this.isSupportReplace = isSupportReplace;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

}
