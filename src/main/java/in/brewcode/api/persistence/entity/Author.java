package in.brewcode.api.persistence.entity;

import in.brewcode.api.persistence.entity.common.CommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "T_AUTHOR")
@Where(clause = "IS_ACTIVE = 'Y'")
public class Author extends CommonEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUTHOR_ID")
	private Long authorId;

	@Column(name = "AUTHOR_USER_NAME", unique = true)
	private String authorUserName;

	@Column(name = "AUTHOR_EMAIL", unique = true)
	private String authorEmail;


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
	super();
	}

	public Long getAuthorId() {
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
