/**
 * 
 */
package com.manicure.keystone.entity.order;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class OrderBase extends BaseEntity {
	private String order_id;
	private String order_status;
	private String order_total_price;
	private String order_create_time;
	private String order_express_price;
	private String buyer_openid;
	private String buyer_nick;
	private String receiver_name;
	private String receiver_province;
	private String receiver_city;
	private String receiver_address;
	private String receiver_mobile;
	private String receiver_phone;
	private String product_id;
	private String product_name;
	private String product_price;
	private String product_sku;
	private String product_count;
	private String product_img;
	private String delivery_id;
	private String delivery_company;
	private String trans_id;
	private String receiver_zone;

	/**
	 * @return the order_id
	 */
	public String getOrder_id() {
		return order_id;
	}

	/**
	 * @param order_id
	 *            the order_id to set
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	/**
	 * @return the order_status
	 */
	public String getOrder_status() {
		return order_status;
	}

	/**
	 * @param order_status
	 *            the order_status to set
	 */
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	/**
	 * @return the order_total_price
	 */
	public String getOrder_total_price() {
		return order_total_price;
	}

	/**
	 * @param order_total_price
	 *            the order_total_price to set
	 */
	public void setOrder_total_price(String order_total_price) {
		this.order_total_price = order_total_price;
	}

	/**
	 * @return the order_create_time
	 */
	public String getOrder_create_time() {
		return order_create_time;
	}

	/**
	 * @param order_create_time
	 *            the order_create_time to set
	 */
	public void setOrder_create_time(String order_create_time) {
		this.order_create_time = order_create_time;
	}

	/**
	 * @return the order_express_price
	 */
	public String getOrder_express_price() {
		return order_express_price;
	}

	/**
	 * @param order_express_price
	 *            the order_express_price to set
	 */
	public void setOrder_express_price(String order_express_price) {
		this.order_express_price = order_express_price;
	}

	/**
	 * @return the buyer_openid
	 */
	public String getBuyer_openid() {
		return buyer_openid;
	}

	/**
	 * @param buyer_openid
	 *            the buyer_openid to set
	 */
	public void setBuyer_openid(String buyer_openid) {
		this.buyer_openid = buyer_openid;
	}

	/**
	 * @return the buyer_nick
	 */
	public String getBuyer_nick() {
		return buyer_nick;
	}

	/**
	 * @param buyer_nick
	 *            the buyer_nick to set
	 */
	public void setBuyer_nick(String buyer_nick) {
		this.buyer_nick = buyer_nick;
	}

	/**
	 * @return the receiver_name
	 */
	public String getReceiver_name() {
		return receiver_name;
	}

	/**
	 * @param receiver_name
	 *            the receiver_name to set
	 */
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	/**
	 * @return the receiver_province
	 */
	public String getReceiver_province() {
		return receiver_province;
	}

	/**
	 * @param receiver_province
	 *            the receiver_province to set
	 */
	public void setReceiver_province(String receiver_province) {
		this.receiver_province = receiver_province;
	}

	/**
	 * @return the receiver_city
	 */
	public String getReceiver_city() {
		return receiver_city;
	}

	/**
	 * @param receiver_city
	 *            the receiver_city to set
	 */
	public void setReceiver_city(String receiver_city) {
		this.receiver_city = receiver_city;
	}

	/**
	 * @return the receiver_address
	 */
	public String getReceiver_address() {
		return receiver_address;
	}

	/**
	 * @param receiver_address
	 *            the receiver_address to set
	 */
	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}

	/**
	 * @return the receiver_mobile
	 */
	public String getReceiver_mobile() {
		return receiver_mobile;
	}

	/**
	 * @param receiver_mobile
	 *            the receiver_mobile to set
	 */
	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}

	/**
	 * @return the receiver_phone
	 */
	public String getReceiver_phone() {
		return receiver_phone;
	}

	/**
	 * @param receiver_phone
	 *            the receiver_phone to set
	 */
	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
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
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}

	/**
	 * @param product_name
	 *            the product_name to set
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	/**
	 * @return the product_price
	 */
	public String getProduct_price() {
		return product_price;
	}

	/**
	 * @param product_price
	 *            the product_price to set
	 */
	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}

	/**
	 * @return the product_sku
	 */
	public String getProduct_sku() {
		return product_sku;
	}

	/**
	 * @param product_sku
	 *            the product_sku to set
	 */
	public void setProduct_sku(String product_sku) {
		this.product_sku = product_sku;
	}

	/**
	 * @return the product_count
	 */
	public String getProduct_count() {
		return product_count;
	}

	/**
	 * @param product_count
	 *            the product_count to set
	 */
	public void setProduct_count(String product_count) {
		this.product_count = product_count;
	}

	/**
	 * @return the product_img
	 */
	public String getProduct_img() {
		return product_img;
	}

	/**
	 * @param product_img
	 *            the product_img to set
	 */
	public void setProduct_img(String product_img) {
		this.product_img = product_img;
	}

	/**
	 * @return the delivery_id
	 */
	public String getDelivery_id() {
		return delivery_id;
	}

	/**
	 * @param delivery_id
	 *            the delivery_id to set
	 */
	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

	/**
	 * @return the delivery_company
	 */
	public String getDelivery_company() {
		return delivery_company;
	}

	/**
	 * @param delivery_company
	 *            the delivery_company to set
	 */
	public void setDelivery_company(String delivery_company) {
		this.delivery_company = delivery_company;
	}

	/**
	 * @return the trans_id
	 */
	public String getTrans_id() {
		return trans_id;
	}

	/**
	 * @param trans_id
	 *            the trans_id to set
	 */
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	/**
	 * @return the receiver_zone
	 */
	public String getReceiver_zone() {
		return receiver_zone;
	}

	/**
	 * @param receiver_zone
	 *            the receiver_zone to set
	 */
	public void setReceiver_zone(String receiver_zone) {
		this.receiver_zone = receiver_zone;
	}

}
