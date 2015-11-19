/**
 * 
 */
package com.fujitsu.keystone.publics.entity.account;

import com.fujitsu.base.entity.BaseEntity;

/**
 * 微信通用接口凭证
 * 
 * @author Barrie
 *
 */
public class WeChatAccessToken extends BaseEntity{
	// 获取到的凭证
	private String token;
	// 凭证有效时间，单位：秒
	private int expiresIn;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the expiresIn
	 */
	public int getExpiresIn() {
		return expiresIn;
	}

	/**
	 * @param expiresIn
	 *            the expiresIn to set
	 */
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
