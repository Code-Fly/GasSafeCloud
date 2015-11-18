/**
 * 
 */
package com.manicure.keystone.entity.product;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class SkuList extends BaseEntity {
	private String sku_id;
	private int price;
	private String icon_url;
	private int quantity;
	private String product_code;
	private int ori_price;

	/**
	 * @return the sku_id
	 */
	public String getSku_id() {
		return sku_id;
	}

	/**
	 * @param sku_id
	 *            the sku_id to set
	 */
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
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

	/**
	 * @return the icon_url
	 */
	public String getIcon_url() {
		return icon_url;
	}

	/**
	 * @param icon_url
	 *            the icon_url to set
	 */
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the product_code
	 */
	public String getProduct_code() {
		return product_code;
	}

	/**
	 * @param product_code
	 *            the product_code to set
	 */
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	/**
	 * @return the ori_price
	 */
	public int getOri_price() {
		return ori_price;
	}

	/**
	 * @param ori_price
	 *            the ori_price to set
	 */
	public void setOri_price(int ori_price) {
		this.ori_price = ori_price;
	}

}
