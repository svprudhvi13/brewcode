package in.brewcode.test.persistence;

import in.brewcode.api.config.PersistenceConfig;
import in.brewcode.api.persistence.dao.IAdminAuthorDao;
import in.brewcode.api.persistence.dao.IArticleDao;
import in.brewcode.api.persistence.entity.Article;
import in.brewcode.api.persistence.entity.Author;
import in.brewcode.api.persistence.entity.Content;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { PersistenceConfig.class })
public class ArticleDaoTest {
	private final Logger log = Logger.getLogger(ArticleDaoTest.class);
	@Autowired
	private IArticleDao articleDao;
	@Autowired
	private IAdminAuthorDao adminAuthorDao;
	
	@Autowired
	private Environment env;
	/**
	 * Test includes creating Content, Author, Article. And Deleting Article(and
	 * Cascaded Contents)
	 */
	@Test
	@Transactional
	public void createArticlewithNoErrors() {
		final Article article = new Article();
		final Author author = new Author();
		author.setAuthorUserName(RandomStringUtils.randomAlphabetic(10));
		
		log.debug("before saving author");
		adminAuthorDao.save(author);
		article.setArticeName(RandomStringUtils.randomAlphabetic(10));
		article.setArticleAuthor(author);
		final Content content = new Content();
		content.setContentBody("hey a ");
		content.setArticle(article);
		List<Content> articleContents = new ArrayList<Content>();
		articleContents.add(content);
		article.setArticleContents(articleContents);
		log.debug("before saving article");
		articleDao.save(article);
	
		List<Article> topfive = articleDao.getTopFiveArticles();
	
		Assert.notNull(topfive);

	}

}
