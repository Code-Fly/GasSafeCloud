/**
 * 
 */
package com.fujitsu.keystone.publics.entity.push.response;

/**
 * @author Barrie
 *
 */
public class VideoMessage extends BaseMessage {

	private Video Video;

	/**
	 * @return the video
	 */
	public Video getVideo() {
		return Video;
	}

	/**
	 * @param video
	 *            the video to set
	 */
	public void setVideo(Video video) {
		Video = video;
	}

}
