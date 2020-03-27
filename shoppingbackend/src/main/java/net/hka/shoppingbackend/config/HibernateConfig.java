package net.hka.shoppingbackend.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * HibernateConfig is designed to let Hibernate to know in advance — where to
 * find the mapping information that defines how your Java classes relate to the
 * database tables. And set the configuration settings related to database and
 * other related parameters.
 *
 */
@Configuration
@ComponentScan(basePackages = { "net.hka.shoppingbackend.dto" })
@EnableTransactionManagement
public class HibernateConfig {

	// Change the below based on the DBMS you choose
	private final static String DATABASE_URL = "jdbc:h2:tcp://localhost/~/onlineshopping";
	private final static String DATABASE_DRIVER = "org.h2.Driver";
	private final static String DATABASE_DIALECT = "org.hibernate.dialect.H2Dialect";
	private final static String DATABASE_USERNAME = "sa";
	private final static String DATABASE_PASSWORD = "";

	// scan packages array for session factory
	// private final static String[] SCAN_PACKAGES_ARRAY =
	// {"net.hka.shoppingbackend.dto"};
	private final static String SCAN_PACKAGE = "net.hka.shoppingbackend.dto";

	// dataSource bean will be available to provide the connection information
	// of the database and it's used in spring-security.xml file as well
	@Bean("dataSource")
	public DataSource getDataSource() {

		BasicDataSource dataSource = new BasicDataSource();

		// Providing the database connection information
		dataSource.setDriverClassName(DATABASE_DRIVER);
		dataSource.setUrl(DATABASE_URL);
		dataSource.setUsername(DATABASE_USERNAME);
		dataSource.setPassword(DATABASE_PASSWORD);

		return dataSource;

	}

	// configure application to use Hibernate and create a session object using
	// the data source provided
	@Bean
	public SessionFactory getSessionFactory(DataSource dataSource) {

		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);

		builder.addProperties(getHibernateProperties());
		builder.scanPackages(SCAN_PACKAGE);

		return builder.buildSessionFactory();

	}

	// all the Hibernate properties will be returned in this method
	private Properties getHibernateProperties() {

		Properties properties = new Properties();

		properties.put("hibernate.dialect", DATABASE_DIALECT);
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");

		// properties.put("hibernate.hbm2ddl.auto", "create");

		// Enabling the Query Cache for second level cache strategy
		// (https://www.tutorialspoint.com/hibernate/hibernate_caching.htm)
		// properties.put("hibernate.cache.use_query_cache",
		// Boolean.TRUE.toString());

		return properties;
	}

	// transactionManager bean will be configured to manage the Hibernate
	// transactions to executing our queries on target database
	// using the session factory provided
	@Bean
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}

}
