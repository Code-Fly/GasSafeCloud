package com.fujitsu.client.entity;

import java.util.List;

public class BarcodegetBottlePsafeResMsg extends BaseEntity {
	public List<BarcodegetBottlePsafeResult> getResult() {
		return result;
	}

	public void setResult(List<BarcodegetBottlePsafeResult> result) {
		this.result = result;
	}

	private List<BarcodegetBottlePsafeResult> result;
}
