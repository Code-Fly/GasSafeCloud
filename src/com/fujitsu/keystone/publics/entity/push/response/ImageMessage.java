/**
 * 
 */
package com.fujitsu.keystone.publics.entity.push.response;

/**
 * @author Barrie
 *
 */
public class ImageMessage extends BaseMessage {

	private Image Image;

	/**
	 * @return the image
	 */
	public Image getImage() {
		return Image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(Image image) {
		Image = image;
	}

}
