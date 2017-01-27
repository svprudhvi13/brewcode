package in.brewcode.api.persistence.dao;

import in.brewcode.api.persistence.entity.PersonalDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonalDetailsDao extends JpaRepository<PersonalDetails,Long> , JpaSpecificationExecutor<PersonalDetails>{

}
