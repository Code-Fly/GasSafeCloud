/**
 * 
 */
package com.manicure.keystone.entity.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.manicure.base.entity.BaseEntity;

/**
 * @author Barrie
 *
 */
public class ProductList extends BaseEntity {
	private int errcode;
	private String errmsg;
	private List<ProductInfo> products_info;

	/**
	 * @return the errcode
	 */
	public int getErrcode() {
		return errcode;
	}

	/**
	 * @param errcode
	 *            the errcode to set
	 */
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	/**
	 * @return the errmsg
	 */
	public String getErrmsg() {
		return errmsg;
	}

	/**
	 * @param errmsg
	 *            the errmsg to set
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/**
	 * @return the products_info
	 */
	public List<ProductInfo> getProducts_info() {
		return products_info;
	}

	/**
	 * @param products_info
	 *            the products_info to set
	 */
	public void setProducts_info(List<ProductInfo> products_info) {
		this.products_info = products_info;
	}

	public void sort(Map<String, String> filter) {
		filter(filter);
		String orderBy = filter.get("orderBy");
		if ("price".equals(orderBy)) {
			sortByPrice(filter);
		} else if ("sales".equals(orderBy)) {
			sortBySales(filter);
		}

	}

	private void sortByPrice(Map<String, String> filter) {
		String sort = filter.get("sort");
		ProductInfo temp; // 记录临时中间值
		int size = products_info.size(); // 数组大小
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				// 交换两数的位置
				if ("asc".equals(sort)) {
					if (products_info.get(i).getSku_list().get(0).getPrice() > products_info.get(j).getSku_list().get(0).getPrice()) {
						temp = products_info.get(i);
						products_info.set(i, products_info.get(j));
						products_info.set(j, temp);
					}
				} else {
					if (products_info.get(i).getSku_list().get(0).getPrice() < products_info.get(j).getSku_list().get(0).getPrice()) {
						temp = products_info.get(i);
						products_info.set(i, products_info.get(j));
						products_info.set(j, temp);
					}
				}

			}
		}
	}

	private void sortBySales(Map<String, String> filter) {
		String sort = filter.get("sort");

		ProductInfo temp; // 记录临时中间值
		int size = products_info.size(); // 数组大小
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				// 交换两数的位置
				if ("asc".equals(sort)) {
					if (Integer.parseInt(products_info.get(i).getProduct_base().getDetail().get(0)) > Integer.parseInt(products_info.get(j).getProduct_base().getDetail().get(0))) {
						temp = products_info.get(i);
						products_info.set(i, products_info.get(j));
						products_info.set(j, temp);
					}
				} else {
					if (Integer.parseInt(products_info.get(i).getProduct_base().getDetail().get(0)) < Integer.parseInt(products_info.get(j).getProduct_base().getDetail().get(0))) {
						temp = products_info.get(i);
						products_info.set(i, products_info.get(j));
						products_info.set(j, temp);
					}
				}

			}
		}
	}

	private void filter(Map<String, String> filter) {
		String minPrice = filter.get("minPrice");
		String maxPrice = filter.get("maxPrice");
		List<ProductInfo> new_products_info = new ArrayList<ProductInfo>();
		int size = products_info.size(); // 数组大小
		for (int i = 0; i < size; i++) {
			if (!"-".equals(minPrice)) {
				if (products_info.get(i).getSku_list().get(0).getPrice() < Integer.parseInt(minPrice) * 100) {
					continue;
				}
			}
			if (!"-".equals(maxPrice)) {
				if (products_info.get(i).getSku_list().get(0).getPrice() > Integer.parseInt(maxPrice) * 100) {
					continue;
				}
			}
			new_products_info.add(products_info.get(i));
		}
		products_info = new_products_info;
	}
}
