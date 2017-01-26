package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.entity.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * Based on method signature, below methods are implemented by Spring Data JPA stores
 * @author ai
 *
 */

public interface IAdminAuthorDao extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

	public Author findByAuthorUserName(String authorUserName);
	public Author findByAuthorEmail(String authorEmail);
}
