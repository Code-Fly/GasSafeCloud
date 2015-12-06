package com.fujitsu.client.entity;

import java.util.List;

public class BarcodegetBottleFillResMsg extends BaseEntity {
 List<BarcodegetBottleFillResult> result;

public List<BarcodegetBottleFillResult> getResult() {
	return result;
}

public void setResult(List<BarcodegetBottleFillResult> result) {
	this.result = result;
}
 
}
