package in.brewcode.test.persistence;

import in.brewcode.api.config.PersistenceConfig;
import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.persistence.dao.IAdminAuthorDao;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.service.IAdminService;
import in.brewcode.api.service.common.ServiceUtils;
import in.brewcode.test.utils.TestUtils;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { PersistenceConfig.class })
public class AdminAuthorDaoTest {


	@Autowired
	private IAdminAuthorDao adminAuthorDao;
	
	@Test
	@Transactional
	public void createAuthorAndNoErrorsGenerated() throws UserAlreadyExistsException {
		AuthorDto authorDto = new AuthorDto();
		authorDto.setAuthorUserName(RandomStringUtils.randomAlphanumeric(8));
	authorDto.setAuthorEmail(RandomStringUtils.randomAlphabetic(7) + TestUtils.EMAIL_EXTENSION);
	
	adminAuthorDao.saveAndFlush(ServiceUtils.convertToAuthorEntity(authorDto, new Author()));
	
	}
	
	
	
}
