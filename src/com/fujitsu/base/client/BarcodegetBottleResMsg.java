package com.fujitsu.base.client;

import java.util.List;

public class BarcodegetBottleResMsg {
	String message;
	int errorCode;
	List<BarcodegetBottleResult> result;
	
	
	public List<BarcodegetBottleResult> getResult() {
		return result;
	}
	public void setResult(List<BarcodegetBottleResult> result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
