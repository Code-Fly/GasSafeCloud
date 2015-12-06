package com.fujitsu.client.entity;

/**
 * Created by Barrie on 15/12/3.
 */
public class GasQPReadingURLResMsg extends BaseEntity {

    GasQPReadingURLResult result;

    public GasQPReadingURLResult getResult() {
        return result;
    }

    public void setResult(GasQPReadingURLResult result) {
        this.result = result;
    }
}
