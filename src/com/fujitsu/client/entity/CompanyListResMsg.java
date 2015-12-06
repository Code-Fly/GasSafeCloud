package com.fujitsu.client.entity;

import java.util.List;

/**
 * Created by Barrie on 15/11/26.
 */
public class CompanyListResMsg extends BaseEntity {

    List<CompanyListResult> result;

    public List<CompanyListResult> getResult() {
        return result;
    }

    public void setResult(List<CompanyListResult> result) {
        this.result = result;
    }
}
