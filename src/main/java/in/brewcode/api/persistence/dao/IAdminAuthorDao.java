package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.dao.common.IAuthorDao;
import in.brewcode.api.persistence.entity.Author;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Based on method signature, below methods are implemented by Spring Data JPA
 * stores
 * 
 * @author ai
 *
 */
@Repository("adminAuthorDao")
public interface IAdminAuthorDao extends IAuthorDao , 
JpaRepository<Author,Long>{

	@Query("FROM Author a WHERE a.role.roleName=:rolename")
	public List<Author> findAllAuthorsByRole(@Param(value ="rolename")String rolename);

}
