package in.brewcode.api.persistence.dao.common;

import in.brewcode.api.auth.server.dao.ICustomUserDao;
import in.brewcode.api.persistence.dao.IAdminAuthorDao;
import in.brewcode.api.persistence.entity.Author;

import org.springframework.data.jpa.repository.Query;

/**
 * Based on method signature, below methods are implemented by Spring Data JPA
 * stores
 * 
 * @author ai
 *
 *This is not used direclty, used as super interface to {@link ICustomUserDao} and {@link IAdminAuthorDao}
 */
public interface IAuthorDao {

	public Author findByAuthorUserName(String authorUserName);
	
		public Author findByAuthorEmail(String authorEmail);

	public Author findByMobileNumber(String mobileNumber);

	@Query("UPDATE Author a SET a.isLocked='Y' WHERE a.authorUserName=:userName")
	public void unlockAuthor(String userName);

	@Query("UPDATE Author a SET a.isLocked='N' WHERE a.authorUserName=:userName")
	public void lockAuthor(String userName);
}
