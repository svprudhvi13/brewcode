package in.brewcode.api.persistence.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "T_CONTENT")
@SQLDelete(sql = "UPDATE T_CONTENT SET IS_ACTIVE='N' WHERE ARTICLE_ID=?")
@Where(clause = "IS_ACTIVE = 'Y'")
public class Content implements Serializable {

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

	/*
	 * Set via Constructor insert only.
	 */
	@Column(name = "CONTENT_CREATED_DATE", insertable = false)
	private Date contentCreatedDate;

	@Generated(GenerationTime.ALWAYS)
	@Column(name = "CONTENT_LAST_EDITED_DATE", insertable = false, updatable = false)
	private Date contentLastEditedDate;

	@Column(name = "IS_ACTIVE")
	private char isActive;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ARTICLE_ID")
	private Article article;

	public String getContentMediaPath() {
		return contentMediaPath;
	}

	public void setContentMediaPath(String contentMediaPath) {
		this.contentMediaPath = contentMediaPath;
	}

	public char getIsActive() {
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
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
		// Created Date set on insert
		this.contentCreatedDate = new java.sql.Date(
				new java.util.Date().getTime());
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

	/*
	 * public void setContentId(int contentId) { this.contentId = contentId; }
	 */
	public Date getContentCreatedDate() {
		return contentCreatedDate;
	}

	public void setContentCreatedDate(Date contentCreatedDate) {
		this.contentCreatedDate = contentCreatedDate;
	}

	public Date getContentLastEditedDate() {
		return contentLastEditedDate;
	}

	public void setContentLastEditedDate(Date contentLastEditedDate) {
		this.contentLastEditedDate = contentLastEditedDate;
	}

}
