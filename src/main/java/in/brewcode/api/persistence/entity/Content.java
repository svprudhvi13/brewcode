package in.brewcode.api.persistence.entity;

import in.brewcode.api.persistence.entity.common.CommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "T_CONTENT")
@SQLDelete(sql = "UPDATE T_CONTENT SET IS_ACTIVE='N' WHERE CONTENT_ID=?")
@Where(clause = "IS_ACTIVE = 'Y'")
public class Content extends CommonEntity implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "CONTENT_ID")
	private long contentId;

	/**
	 * Tweet html goes here
	 */
	@Column(name = "CONTENT_BODY")
	@Type(type = "text")
	private String contentBody;

	/*
	 * Location string image/gif/video goes here
	 */
	@Column(name = "CONTENT_MEDIA_PATH")
	private String contentMediaPath;


	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ARTICLE_ID")
	private Article article;

	public String getContentMediaPath() {
		return contentMediaPath;
	}

	public void setContentMediaPath(String contentMediaPath) {
		this.contentMediaPath = contentMediaPath;
	}
	public void setContentId(long contentId) {
		this.contentId = contentId;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	/**
	 * No argument constructor of this entity / persistent class
	 */
	public Content() {
	}

	public String getContentBody() {
		return contentBody;
	}

	public void setContentBody(String contentBody) {
		this.contentBody = contentBody;
	}

	public long getContentId() {
		return contentId;
	}


}
