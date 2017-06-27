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
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "T_AUTHOR")
@Where(clause = "IS_ACTIVE = 'Y'")
public class Author extends CommonEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2960311956692763153L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUTHOR_ID")
	private Long authorId;

	@Column(name = "AUTHOR_USER_NAME", unique = true)
	private String authorUserName;

	@Column(name = "AUTHOR_PASSWORD")
	private String password;

	@Column(name = "IS_LOCKED")
	private char isLocked;

	@Column(name = "AUTHOR_EMAIL", unique = true)
	private String authorEmail;

	@Column(name = "AUTHOR_MOBILE_NUMBER")
	private String mobileNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTHOR_ROLE_ID")
	private Role role;
/*
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private PersonalDetails personalDetails;

*/	@PrePersist
	private void init() {
		setIsLocked('Y');
	}

	public String getPassword() {
		return password;
	}

	public char getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(char isLocked) {
		this.isLocked = isLocked;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

/*	public PersonalDetails getPersonalDetails() {
		return personalDetails;
	}

	public void setPersonalDetails(PersonalDetails personalDetails) {
		this.personalDetails = personalDetails;
	}
*/
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
