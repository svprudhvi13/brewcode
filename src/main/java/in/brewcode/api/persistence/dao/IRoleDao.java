package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleDao extends JpaRepository<Role, Long> {

	public Role findByRoleNameIgnoreCase(String roleName);
}
