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
public class ProductBase extends BaseEntity {
	private String name;
	private List<Integer> category_id;
	/**
	 * @return the category_id
	 */
	public List<Integer> getCategory_id() {
		return category_id;
	}

	/**
	 * @param category_id
	 *            the category_id to set
	 */
	public void setCategory_id(List<Integer> category_id) {
		this.category_id = category_id;
	}

	private List<String> img;
	private List<Property> property;
	private List<SkuInfo> sku_info;
	private int buy_limit;
	private String main_img;
	private String detail_html;
	private List<String> detail;

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

	/**
	 * @return the img
	 */
	public List<String> getImg() {
		return img;
	}

	/**
	 * @param img
	 *            the img to set
	 */
	public void setImg(List<String> img) {
		this.img = img;
	}

	/**
	 * @return the property
	 */
	public List<Property> getProperty() {
		return property;
	}

	/**
	 * @param property
	 *            the property to set
	 */
	public void setProperty(List<Property> property) {
		this.property = property;
	}

	/**
	 * @return the sku_info
	 */
	public List<SkuInfo> getSku_info() {
		return sku_info;
	}

	/**
	 * @param sku_info
	 *            the sku_info to set
	 */
	public void setSku_info(List<SkuInfo> sku_info) {
		this.sku_info = sku_info;
	}

	/**
	 * @return the buy_limit
	 */
	public int getBuy_limit() {
		return buy_limit;
	}

	/**
	 * @param buy_limit
	 *            the buy_limit to set
	 */
	public void setBuy_limit(int buy_limit) {
		this.buy_limit = buy_limit;
	}

	/**
	 * @return the main_img
	 */
	public String getMain_img() {
		return main_img;
	}

	/**
	 * @param main_img
	 *            the main_img to set
	 */
	public void setMain_img(String main_img) {
		this.main_img = main_img;
	}

	/**
	 * @return the detail_html
	 */
	public String getDetail_html() {
		return detail_html;
	}

	/**
	 * @param detail_html
	 *            the detail_html to set
	 */
	public void setDetail_html(String detail_html) {
		this.detail_html = detail_html;
	}

	/**
	 * @return the detail
	 */
	public List<String> getDetail() {
		return detail;
	}

	/**
	 * @param detail
	 *            the detail to set
	 */
	public void setDetail(List<String> detail) {
		this.detail = detail;
	}

}
