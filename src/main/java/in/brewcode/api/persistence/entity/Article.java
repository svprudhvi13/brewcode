package in.brewcode.api.persistence.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "T_ARTICLE")
@SQLDelete(sql = "UPDATE T_ARTICLE SET IS_ACTIVE='N' WHERE ARTICLE_ID=?")
@Where(clause = "IS_ACTIVE = 'Y'")
public class Article implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "ARTICLE_ID")
	private long articleId;

	@Column(name = "ARTICLE_NAME", nullable=false, unique=true)
	private String articleName;

	@Column(name = "ARTICLE_IMAGE")
	private String articleImageLocation;

	@Generated(GenerationTime.INSERT)
	@Column(name = "ARTICLE_CREATED_DATE", insertable=false)
	private Date articleCreatedDate;

	@Generated(GenerationTime.ALWAYS)
	@Column(name = "ARTICLE_LAST_EDITED_DATE", insertable = false, updatable = false)
	private Date articleLastEditedDate;

	@Column(name = "ARTICLE_PUBLISHED_DATE")
	private Date articlePublishedDate;

	@Column(name = "IS_ACTIVE")
	private char isActive;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTHOR_ID")
	private Author articleAuthor;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL)
	@Where(clause = "IS_ACTIVE= 'Y'")
	private List<Content> articleContents;

	/**
	 * No argument constructor of this entity / persistent class
	 */
	public Article() {
		/*
		 * This field is updated on insert
		 */
		this.articleCreatedDate = new Date(new java.util.Date().getTime());
	}

	public Date getArticleCreatedDate() {
		return articleCreatedDate;
	}

	public void setArticleCreatedDate(Date articleCreatedDate) {
		this.articleCreatedDate = articleCreatedDate;
	}

	public Date getArticleLastEditedDate() {
		return articleLastEditedDate;
	}

	public void setArticleLastEditedDate(Date articleLastEditedDate) {
		this.articleLastEditedDate = articleLastEditedDate;
	}

	public Date getArticlePublishedDate() {
		return articlePublishedDate;
	}

	public void setArticlePublishedDate(Date articlePublishedDate) {
		this.articlePublishedDate = articlePublishedDate;
	}

	public long getArticleId() {
		return articleId;
	}

	/*
	 * public void setArticleId(int articleId) { this.articleId = articleId; }
	 */
	public String getArticeName() {
		return articleName;
	}

	public void setArticeName(String articeName) {
		this.articleName = articeName;
	}

	public String getArticleImageLocation() {
		return articleImageLocation;
	}

	public void setArticleImageLocation(String articleImageLocation) {
		this.articleImageLocation = articleImageLocation;
	}

	public Author getArticleAuthor() {
		return articleAuthor;
	}

	public void setArticleAuthor(Author articleAuthor) {
		this.articleAuthor = articleAuthor;
	}

	public List<Content> getArticleContents() {
		return articleContents;
	}

	public void setArticleContents(List<Content> articleContents) {
		this.articleContents = articleContents;
	}

	public char getIsActive() {
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}

}
