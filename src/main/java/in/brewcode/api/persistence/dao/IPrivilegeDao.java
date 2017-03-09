package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.entity.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPrivilegeDao extends JpaRepository<Privilege, Long>{

	public Privilege findByPrivilegeNameIgnoreCase(String privilegeName);
	
	@Modifying
	@Query("UPDATE Privilege p SET p.isActive='N' WHERE p.privilegeName=:privilegeName")
	public void deleteByPrivilegeNameIgnoreCase(@Param(value="privilegeName")String privilgeName);

	}
