package in.brewcode.api.persistence.entity.common;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class CommonEntity {
	@Column(name = "IS_ACTIVE")
	private char isActive;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name = "LAST_UPDATED_DATE")
	private LocalDateTime lastUpdatedDate;

	@PrePersist
	protected void onCreate() {

		this.createdDate = this.lastUpdatedDate = LocalDateTime.now();
		this.isActive = 'Y';
	}

	@PreUpdate
	protected void onUpdate() {
		this.lastUpdatedDate = LocalDateTime.now();

	}

	public LocalDateTime getCreateDate() {
		return createdDate;
	}

	public LocalDateTime getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public char getIsActive() {
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}

}
