package in.brewcode.api.persistence.entity;

import in.brewcode.api.persistence.entity.common.CommonEntity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private int id;
	
	@Column(name="ROLE")
	private int roleName;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="T_ROLE_PRIVILEGE_MAPPING", joinColumns={@JoinColumn(name="ROLE_ID")},
	inverseJoinColumns={@JoinColumn(name="PRIVILEGE_ID")})
	private Set<Privilege> rolePrivileges;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleName() {
		return roleName;
	}

	public void setRoleName(int roleName) {
		this.roleName = roleName;
	}

	public Set<Privilege> getRolePrivileges() {
		return rolePrivileges;
	}

	public void setRolePrivileges(Set<Privilege> rolePrivileges) {
		this.rolePrivileges = rolePrivileges;
	}
	
}
