package in.brewcode.test.persistence;

import in.brewcode.api.config.PersistenceConfig;
import in.brewcode.api.persistence.dao.IAdminAuthorDao;
import in.brewcode.api.persistence.entity.Author;

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
	public void createAuthorAndNoErrorsGenerated() {
		Author author = new Author();
		System.out.println(author.getCreateDate());
		author.setAuthorUserName(RandomStringUtils.randomAlphanumeric(8));
	adminAuthorDao.save(author);
	System.out.println("Good save "+ 	author.getCreateDate());
	adminAuthorDao.update(author);
	}

}
