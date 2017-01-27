package in.brewcode.api.persistence.dao;

import in.brewcode.api.exception.PrivilegeNotFoundException;
import in.brewcode.api.persistence.entity.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPrivilegeDao extends JpaRepository<Privilege, Long>{

	public Privilege findByPrivilegeNameIgnoreCase(String privilegeName);
}
