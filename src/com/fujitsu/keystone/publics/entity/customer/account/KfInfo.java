package com.fujitsu.keystone.publics.entity.customer.account;

import com.fujitsu.base.entity.BaseEntity;

/**
 * Created by Barrie on 15/12/18.
 */

public class KfInfo extends BaseEntity {
    private String kf_account;
    private String nickname;
    private String password;

    public String getKf_account() {
        return kf_account;
    }

    public void setKf_account(String kf_account) {
        this.kf_account = kf_account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
