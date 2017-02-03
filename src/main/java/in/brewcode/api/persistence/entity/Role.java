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
@Table(name="T_ROLE")
@Where(clause = "IS_ACTIVE = 'Y'")

public class Role extends CommonEntity implements Serializable{
	@Id
	@Column(name="ROLE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="ROLE", unique=true)
	private String roleName;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="T_ROLE_PRIVILEGE_MAPPING", joinColumns={@JoinColumn(name="ROLE_ID")},
	inverseJoinColumns={@JoinColumn(name="PRIVILEGE_ID")})
	private Set<Privilege> rolePrivileges;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<Privilege> getRolePrivileges() {
		return rolePrivileges;
	}

	public void setRolePrivileges(Set<Privilege> rolePrivileges) {
		this.rolePrivileges = rolePrivileges;
	}
	
}
