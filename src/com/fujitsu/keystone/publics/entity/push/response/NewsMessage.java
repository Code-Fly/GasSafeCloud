/**
 * 
 */
package com.fujitsu.keystone.publics.entity.push.response;

import java.util.List;

/**
 * @author Barrie
 *
 */
public class NewsMessage extends BaseMessage {
	// 图文消息个数，限制为10条以内
	private int ArticleCount;
	// 多条图文消息信息，默认第一个item为大图
	private List<Article> Articles;

	/**
	 * @return the articleCount
	 */
	public int getArticleCount() {
		return ArticleCount;
	}

	/**
	 * @param articleCount
	 *            the articleCount to set
	 */
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	/**
	 * @return the articles
	 */
	public List<Article> getArticles() {
		return Articles;
	}

	/**
	 * @param articles
	 *            the articles to set
	 */
	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

}
