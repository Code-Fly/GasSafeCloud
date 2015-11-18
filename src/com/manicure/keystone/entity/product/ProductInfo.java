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
public class ProductInfo extends BaseEntity {
	private ProductBase product_base;
	private List<SkuList> sku_list;
	private DeliveryInfo delivery_info;
	private String product_id;
	private int status;
	private AttrExt attrext;

	/**
	 * @return the product_base
	 */
	public ProductBase getProduct_base() {
		return product_base;
	}

	/**
	 * @param product_base
	 *            the product_base to set
	 */
	public void setProduct_base(ProductBase product_base) {
		this.product_base = product_base;
	}

	/**
	 * @return the sku_list
	 */
	public List<SkuList> getSku_list() {
		return sku_list;
	}

	/**
	 * @param sku_list
	 *            the sku_list to set
	 */
	public void setSku_list(List<SkuList> sku_list) {
		this.sku_list = sku_list;
	}

	/**
	 * @return the delivery_info
	 */
	public DeliveryInfo getDelivery_info() {
		return delivery_info;
	}

	/**
	 * @param delivery_info
	 *            the delivery_info to set
	 */
	public void setDelivery_info(DeliveryInfo delivery_info) {
		this.delivery_info = delivery_info;
	}

	/**
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}

	/**
	 * @param product_id
	 *            the product_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the attrext
	 */
	public AttrExt getAttrext() {
		return attrext;
	}

	/**
	 * @param attrext
	 *            the attrext to set
	 */
	public void setAttrext(AttrExt attrext) {
		this.attrext = attrext;
	}

}
