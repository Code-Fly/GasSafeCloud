package com.fujitsu.client.entity;

import java.util.List;

/**
 * Created by Barrie on 15/11/27.
 */
public class CompanyDetailResMsg extends BaseEntity {

    List<CompanyDetailResult> result;

    public List<CompanyDetailResult> getResult() {
        return result;
    }

    public void setResult(List<CompanyDetailResult> result) {
        this.result = result;
    }
}
