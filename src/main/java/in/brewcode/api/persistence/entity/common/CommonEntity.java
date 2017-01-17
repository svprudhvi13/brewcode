package in.brewcode.api.persistence.entity.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class CommonEntity {
	@Column(name = "IS_ACTIVE")
	private char isActive;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdatedDate;

	@PrePersist
	protected void onCreate() {

		this.createdDate = this.lastUpdatedDate = new Date();
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

}
