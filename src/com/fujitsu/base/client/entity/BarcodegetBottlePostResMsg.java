/**
 * 
 */
package com.fujitsu.base.client.entity;

import java.util.List;

/**
 * @author VM
 *
 */
public class BarcodegetBottlePostResMsg extends BaseEntity {
 List<BarcodegetBottlePostResult> result;

public List<BarcodegetBottlePostResult> getResult() {
	return result;
}

public void setResult(List<BarcodegetBottlePostResult> result) {
	this.result = result;
}
 
}
