package in.brewcode.api.persistence.entity;

import in.brewcode.api.persistence.entity.common.CommonEntity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name="T_PRIVILEGE")
@Where(clause = "IS_ACTIVE = 'Y'")

public class Privilege extends CommonEntity implements Serializable {
	
	@Id
	@Column(name="PRIVILEGE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="PRIVILEGE_NAME")
	private String privilegeName;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name="T_ROLE_PRIVILEGE_MAPPING", joinColumns={@JoinColumn(name="ROLE_ID")},
	inverseJoinColumns={@JoinColumn(name="PRIVILEGE_ID")})
	
	private Set<Role> roles;

	public String getPrivilegeName() {
		return privilegeName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	
	
}
