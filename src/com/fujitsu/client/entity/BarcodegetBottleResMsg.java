package com.fujitsu.client.entity;

import java.util.List;

public class BarcodegetBottleResMsg extends BaseEntity {
	List<BarcodegetBottleResult> result;
	public List<BarcodegetBottleResult> getResult() {
		return result;
	}
	public void setResult(List<BarcodegetBottleResult> result) {
		this.result = result;
	}
	
}
