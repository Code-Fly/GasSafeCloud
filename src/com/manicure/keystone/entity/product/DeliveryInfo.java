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
public class DeliveryInfo extends BaseEntity {
	private int delivery_type;
	private String template_id;
	private String weight;
	private String volume;
	private List<Express> express;

	/**
	 * @return the delivery_type
	 */
	public int getDelivery_type() {
		return delivery_type;
	}

	/**
	 * @param delivery_type
	 *            the delivery_type to set
	 */
	public void setDelivery_type(int delivery_type) {
		this.delivery_type = delivery_type;
	}

	/**
	 * @return the template_id
	 */
	public String getTemplate_id() {
		return template_id;
	}

	/**
	 * @param template_id
	 *            the template_id to set
	 */
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *            the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}

	/**
	 * @return the express
	 */
	public List<Express> getExpress() {
		return express;
	}

	/**
	 * @param express
	 *            the express to set
	 */
	public void setExpress(List<Express> express) {
		this.express = express;
	}

}
