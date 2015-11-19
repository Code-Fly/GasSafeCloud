/**
 * 
 */
package com.fujitsu.keystone.publics.entity.customer.message;

/**
 * @author Barrie
 *
 */
public class TextMessage extends BaseMessage {
	private Text text;

	/**
	 * @return the text
	 */
	public Text getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(Text text) {
		this.text = text;
	}

}
