package in.brewcode.api.persistence.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "T_ADMIN")
public class Admin {
	@Id
	@Column(name = "ADMIN_ID")
	@GeneratedValue
	private long adminId;

	@Generated(GenerationTime.ALWAYS)
	@Column(name = "ADMIN_CREATED_DATE", insertable = false)
	private Date adminCreatedDate;

	@Column(name = "ADMIN_EXPIRY_DATE")
	private Date adminExpiryDate;

	@Column(name = "IS_ACTIVE")
	private boolean isActive;

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public Date getAdminCreatedDate() {
		return adminCreatedDate;
	}

	public void setAdminCreatedDate(Date adminCreatedDate) {
		this.adminCreatedDate = adminCreatedDate;
	}

	public Date getAdminExpiryDate() {
		return adminExpiryDate;
	}

	public void setAdminExpiryDate(Date adminExpiryDate) {
		this.adminExpiryDate = adminExpiryDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
