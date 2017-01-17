package in.brewcode.api.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
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
public class PersistenceConfig {

	/**
	 * Loads properties from above mentioned @PropertySource values
	 */
	private static final String ENTITY_PACKAGE="in.brewcode.api.persistence.entity";
	@Autowired
	private Environment env;

	public PersistenceConfig() {
		super();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(restDataSource());
		entityManager.setPackagesToScan(new String[]{ENTITY_PACKAGE});
		entityManager.setJpaProperties(hibernateProperties());
		JpaVendorAdapter hibernateVendorAdapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(hibernateVendorAdapter);
		
	return entityManager;
	}

	@Bean
	public DataSource restDataSource() {
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
	public PlatformTransactionManager transactionManager() {
		 JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory( entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	final Properties hibernateProperties() {
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
	//@Autowired
	public static PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
