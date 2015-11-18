/**
 * 
 */
package com.manicure.keystone.entity.shop;

import java.util.List;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class BaseInfo extends BaseEntity {
	private String sid;
	private String business_name;
	private String branch_name;
	private String address;
	private String telephone;
	private List<String> categories;
	private String city;
	private String province;
	private String offset_type;
	private String longitude;
	private String latitude;
	private List<PhotoUrl> photo_list;
	private String introduction;
	private String recommend;
	private String special;
	private String open_time;
	private String avg_price;
	private String poi_id;
	private String available_state;
	private String district;
	private String update_status;

	/**
	 * @return the sid
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * @param sid
	 *            the sid to set
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * @return the business_name
	 */
	public String getBusiness_name() {
		return business_name;
	}

	/**
	 * @param business_name
	 *            the business_name to set
	 */
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	/**
	 * @return the branch_name
	 */
	public String getBranch_name() {
		return branch_name;
	}

	/**
	 * @param branch_name
	 *            the branch_name to set
	 */
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the offset_type
	 */
	public String getOffset_type() {
		return offset_type;
	}

	/**
	 * @param offset_type
	 *            the offset_type to set
	 */
	public void setOffset_type(String offset_type) {
		this.offset_type = offset_type;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the photo_list
	 */
	public List<PhotoUrl> getPhoto_list() {
		return photo_list;
	}

	/**
	 * @param photo_list
	 *            the photo_list to set
	 */
	public void setPhoto_list(List<PhotoUrl> photo_list) {
		this.photo_list = photo_list;
	}

	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param introduction
	 *            the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * @return the recommend
	 */
	public String getRecommend() {
		return recommend;
	}

	/**
	 * @param recommend
	 *            the recommend to set
	 */
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	/**
	 * @return the special
	 */
	public String getSpecial() {
		return special;
	}

	/**
	 * @param special
	 *            the special to set
	 */
	public void setSpecial(String special) {
		this.special = special;
	}

	/**
	 * @return the open_time
	 */
	public String getOpen_time() {
		return open_time;
	}

	/**
	 * @param open_time
	 *            the open_time to set
	 */
	public void setOpen_time(String open_time) {
		this.open_time = open_time;
	}

	/**
	 * @return the avg_price
	 */
	public String getAvg_price() {
		return avg_price;
	}

	/**
	 * @param avg_price
	 *            the avg_price to set
	 */
	public void setAvg_price(String avg_price) {
		this.avg_price = avg_price;
	}

	/**
	 * @return the poi_id
	 */
	public String getPoi_id() {
		return poi_id;
	}

	/**
	 * @param poi_id
	 *            the poi_id to set
	 */
	public void setPoi_id(String poi_id) {
		this.poi_id = poi_id;
	}

	/**
	 * @return the available_state
	 */
	public String getAvailable_state() {
		return available_state;
	}

	/**
	 * @param available_state
	 *            the available_state to set
	 */
	public void setAvailable_state(String available_state) {
		this.available_state = available_state;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district
	 *            the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the update_status
	 */
	public String getUpdate_status() {
		return update_status;
	}

	/**
	 * @param update_status
	 *            the update_status to set
	 */
	public void setUpdate_status(String update_status) {
		this.update_status = update_status;
	}

}
