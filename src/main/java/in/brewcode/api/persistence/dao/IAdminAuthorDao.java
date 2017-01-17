package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.entity.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface IAdminAuthorDao extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
//
	public Author findByAuthorUserName(String userName);
}
