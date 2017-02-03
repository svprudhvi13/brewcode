package in.brewcode.test.persistence;

import in.brewcode.api.config.PersistenceConfig;
import in.brewcode.api.dto.ArticleDto;
import in.brewcode.api.dto.AuthorDto;
import in.brewcode.api.dto.ContentDto;
import in.brewcode.api.exception.UserAlreadyExistsException;
import in.brewcode.api.service.IAdminService;
import in.brewcode.api.service.IArticleAndContentService;
import in.brewcode.test.utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { PersistenceConfig.class })
public class ArticleDaoTest {
	private final Logger log = Logger.getLogger(ArticleDaoTest.class);
	@Autowired
	private IAdminService adminService;
	@Autowired
	private IArticleAndContentService articleAndContentService;
	/**
	 * Test includes creating Content, Author, Article. And Deleting Article(and
	 * Cascaded Contents)
	 */
	private AuthorDto authorDto;
	@Before
	@Transactional
	public void init() throws UserAlreadyExistsException{
		authorDto = new AuthorDto();
		String userName = RandomStringUtils.randomAlphabetic(10);
		authorDto.setAuthorUserName(userName);
		authorDto.setAuthorEmail(RandomStringUtils.randomAlphanumeric(7)+TestUtils.EMAIL_EXTENSION);
		adminService.createAuthor(authorDto);
		
	}
	@Test
	@Ignore
	@Transactional
	public void createArticlewithNoErrors() {
		ArticleDto articleDto = new ArticleDto();
		
		AuthorDto authorDtoLocal = null;
		log.debug("before saving author");
		authorDtoLocal = adminService.findByUserName(authorDto.getAuthorUserName());
		articleDto.setArticleName(RandomStringUtils.randomAlphabetic(10));
		articleDto.setArticleAuthorDto(authorDtoLocal);
		ContentDto contentDto = new ContentDto();
		contentDto.setContentDtoBody(RandomStringUtils.randomAlphanumeric(50));
		contentDto.setContentDtoMediaPath(RandomStringUtils.randomAlphabetic(20));
		List<ContentDto> articleContentDtos = new ArrayList<ContentDto>();
		articleContentDtos.add(contentDto);
		articleDto.setArticleContentDtos(articleContentDtos);
		log.debug("before saving article");
		articleAndContentService.saveArticle(articleDto);
	
	//	articleAndContentService.
		
	}

}
