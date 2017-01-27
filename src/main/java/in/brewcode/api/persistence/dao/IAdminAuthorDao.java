package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.entity.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Based on method signature, below methods are implemented by Spring Data JPA
 * stores
 * 
 * @author ai
 *
 */
@Repository
public interface IAdminAuthorDao extends JpaRepository<Author, Long>,
		JpaSpecificationExecutor<Author> {

	public Author findByAuthorUserName(String authorUserName);
	
		public Author findByAuthorEmail(String authorEmail);

	public Author findByMobileNumber(String mobileNumber);

	@Query("UPDATE Author a SET a.isLocked='Y' WHERE a.authorUserName=:userName")
	public void unlockAuthor(String userName);

	@Query("UPDATE Author a SET a.isLocked='N' WHERE a.authorUserName=:userName")
	public void lockAuthor(String userName);
}
