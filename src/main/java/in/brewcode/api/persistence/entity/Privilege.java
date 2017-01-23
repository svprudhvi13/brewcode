package in.brewcode.api.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name="T_PRIVILEGE")
@Where(clause = "IS_ACTIVE = 'Y'")

public class Privilege implements Serializable {
	
	@Id
	@Column(name="PRIVILEGE_ID")
	private int id;
	
	@Column(name="PRIVILEGE_NAME")
	private String privilegeName;
	
	@Column(name="IS_ACTIVE")
	private char isActive;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public char getIsActive() {
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	
	
	
}
