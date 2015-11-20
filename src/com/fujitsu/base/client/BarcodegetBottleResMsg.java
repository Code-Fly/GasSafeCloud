package com.fujitsu.base.client;

public class BarcodegetBottleResMsg {
	String message;
	int errorCode;
	BarcodegetBottleResult result;
	
	public BarcodegetBottleResult getResult() {
		return result;
	}
	public void setResult(BarcodegetBottleResult result) {
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
