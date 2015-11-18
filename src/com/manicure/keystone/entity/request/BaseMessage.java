/**
 * 
 */
package com.manicure.keystone.entity.request;

import com.manicure.base.entity.BaseEntity;

/**
 * 消息基类（普通用户 -> 公众帐号）
 * 
 * @author Barrie
 * 
 */
public class BaseMessage extends BaseEntity {
	// 开发者微信号
	private String ToUserName;
	// 发送方帐号（一个OpenID）
	private String FromUserName;
	// 消息创建时间 （整型）
	private long CreateTime;
	// 消息类型（text/image/location/link）
	private String MsgType;
	// 消息id，64位整型
	private long MsgId;

	/**
	 * @return the toUserName
	 */
	public String getToUserName() {
		return ToUserName;
	}

	/**
	 * @param toUserName
	 *            the toUserName to set
	 */
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	/**
	 * @return the fromUserName
	 */
	public String getFromUserName() {
		return FromUserName;
	}

	/**
	 * @param fromUserName
	 *            the fromUserName to set
	 */
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	/**
	 * @return the createTime
	 */
	public long getCreateTime() {
		return CreateTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return MsgType;
	}

	/**
	 * @param msgType
	 *            the msgType to set
	 */
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	/**
	 * @return the msgId
	 */
	public long getMsgId() {
		return MsgId;
	}

	/**
	 * @param msgId
	 *            the msgId to set
	 */
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}

}
