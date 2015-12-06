package com.fujitsu.client.entity;

/**
 * Created by Barrie on 15/12/3.
 */
public class GasQPReadingURLResult extends com.fujitsu.base.entity.BaseEntity {
    private String gasUrl;
    private String qpUrl;

    public String getGasUrl() {
        return gasUrl;
    }

    public void setGasUrl(String gasUrl) {
        this.gasUrl = gasUrl;
    }

    public String getQpUrl() {
        return qpUrl;
    }

    public void setQpUrl(String qpUrl) {
        this.qpUrl = qpUrl;
    }
}
