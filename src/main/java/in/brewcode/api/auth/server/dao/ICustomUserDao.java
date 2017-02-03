package in.brewcode.api.auth.server.dao;

import in.brewcode.api.persistence.dao.common.IAuthorDao;
import in.brewcode.api.persistence.entity.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("customUserDao")
public interface ICustomUserDao extends IAuthorDao , 
JpaRepository<Author,Long>{


}