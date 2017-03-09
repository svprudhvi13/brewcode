package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleDao extends JpaRepository<Role, Long> {

	public Role findByRoleNameIgnoreCase(String roleName);
	
	@Modifying
	@Query("UPDATE Role r SET r.isActive='N' WHERE r.roleName=:roleName")
	public void deleteByRoleNameIgnoreCase(@Param(value="roleName")String roleName);
	
}
