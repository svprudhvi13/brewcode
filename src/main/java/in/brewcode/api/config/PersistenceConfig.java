package in.brewcode.api.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.base.Preconditions;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/persistence-${envTarget:mysql}.properties")
@ComponentScan(basePackages={"in.brewcode.api.persistence", "in.brewcode.api.service"} )
@EnableJpaRepositories(basePackages = "in.brewcode.api.persistence.dao", queryLookupStrategy = Key.CREATE_IF_NOT_FOUND)

public class PersistenceConfig {

	private static Logger logger = Logger.getLogger(PersistenceConfig.class);
	/**
	 * Loads properties from above mentioned @PropertySource values
	 */
	@Autowired
	private Environment env;

	private static final String ENTITY_PACKAGE="in.brewcode.api.persistence.entity";

	public PersistenceConfig() {
		super();
		logger.info("PersistenceConfig configuration initializing");
	}


	@Bean
	public DataSource restDataSource() {
		logger.info("Initializing restDataSource..");
		final BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(Preconditions.checkNotNull(env
				.getProperty("jdbc.driverClassName")));
		dataSource.setUrl(Preconditions.checkNotNull(env
				.getProperty("jdbc.url")));
		dataSource.setUsername(Preconditions.checkNotNull(env
				.getProperty("jdbc.user")));
		dataSource.setPassword(Preconditions.checkNotNull(env
				.getProperty("jdbc.password")));

		return dataSource;
	}

	@Bean
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		logger.info("Initializing LocalContainerEntityManagerFactoryBean..");
		final LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(restDataSource());
		entityManager.setPackagesToScan(new String[]{ENTITY_PACKAGE});
		entityManager.setJpaProperties(hibernateProperties());
		JpaVendorAdapter hibernateVendorAdapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(hibernateVendorAdapter);
		
	return entityManager;
	}

	/**
	 * Gets autowired {@link LocalContainerEntityManagerFactoryBean} from above
	 * @param entityManagerFactory
	 * @return
	 */
	@Bean
	//@Autowired
	public PlatformTransactionManager transactionManager(final LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		 JpaTransactionManager transactionManager = new JpaTransactionManager();
		 logger.info("Initializing PlatformTransactionManager..");
		 transactionManager.setEntityManagerFactory( entityManagerFactory.getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		logger.info("Initializing PersistenceExceptionTranslationPostProcessor");
		return new PersistenceExceptionTranslationPostProcessor();
	}

	final Properties hibernateProperties() {
logger.info("Getting hibernate properties..");
		final Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto",
				env.getProperty("hibernate.hbm2ddl.auto"));
		hibernateProperties.setProperty("hibernate.dialect",
				env.getProperty("hibernate.dialect"));

		hibernateProperties.setProperty("hibernate.show_sql", "true");
		// hibernateProperties.setProperty("hibernate.format_sql", "true");
		// hibernateProperties.setProperty("hibernate.globally_quoted_identifiers",
		// "true");

		return hibernateProperties;
	}

	/**
	 * Used to resolve ${}. Here, however ${envTarget} is not configured Here,
	 * static should be used as the bean implements BeanFactoryPostProcessor
	 * interface and this bean should be loaded by Spring IOC before the @Configuration
	 * class load
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
		logger.info("Loading PropertySourcesPlaceholderConfigurer");
		return new PropertySourcesPlaceholderConfigurer();
	}

	/**
	 * This bean is to create tables from schema.sql for Spring Oauth
	 * Commented now, 
	 */
	
/*	@Bean
	public DataSourceInitializer dataSourceInitializer(
			final DataSource dataSource) {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
	logger.info("Initializing Data Source: "+ dataSource.getClass().toString());
		*//**
	 * Removed after adding one client
	 *//*
		//	initializer.setDatabasePopulator(databasePopulator());
		return initializer;
	}
*/	/**
	 * Note to dev: Add all the tables of application in the resource here
	 */
	/**
	 * Removed after adding one client
	 */
	/*
	@Value("classpath:/schema.sql")
	private Resource schemaScript;
	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(schemaScript);
	//	populator.addScript(dataScript);
		return populator;
	}
*/
}
