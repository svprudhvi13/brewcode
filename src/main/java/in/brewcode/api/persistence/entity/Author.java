package in.brewcode.api.persistence.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "T_AUTHOR")
@Where(clause = "IS_ACTIVE = 'Y'")
public class Author implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUTHOR_ID")
	private long authorId;

	@Column(name = "AUTHOR_USER_NAME", unique = true)
	private String authorUserName;

	@Column(name = "AUTHOR_EMAIL", unique = true)
	private String authorEmail;

	@Column(name = "IS_ACTIVE")
	private char isActive;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdatedDate;

	@PrePersist
	protected void onCreate() {

		this.createdDate = this.lastUpdatedDate = new Date(
				new java.util.Date().getTime());
		this.isActive = 'Y';
	}

	@PreUpdate
	protected void onUpdate() {
		this.lastUpdatedDate = new Date(new java.util.Date().getTime());

	}

	public Date getCreateDate() {
		return createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public char getIsActive() {
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTHOR_ROLE_ID")
	private Role role;

	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * No argument constructor of this entity / persistent class
	 */
	public Author() {
	}

	public long getAuthorId() {
		return authorId;
	}

	/*
	 * public void setAuthorId(int authorId) { this.authorId = authorId; }
	 */
	public String getAuthorUserName() {
		return authorUserName;
	}

	public void setAuthorUserName(String authorUserName) {
		this.authorUserName = authorUserName;
	}

}
