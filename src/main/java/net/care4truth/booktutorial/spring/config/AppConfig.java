package net.care4truth.booktutorial.spring.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("net.care4truth.booktutorial.spring.dao"),
@ComponentScan("net.care4truth.booktutorial.spring.service") })
public class AppConfig {

   @Autowired
   private Environment env;

   @Bean
   public LocalSessionFactoryBean getSessionFactory() {
      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

      Properties props = new Properties();
    
      factoryBean.setDataSource(getDriverManagerDataSource());
      factoryBean.setHibernateProperties(hibernateProperties());
      factoryBean.setPackagesToScan("net.care4truth.booktutorial.spring.model");

      return factoryBean;
   }
   
   Properties hibernateProperties() {
	      return new Properties() {
	         /**
	         * 
	         */
	        private static final long serialVersionUID = 1L;

	        {
	            setProperty("hibernate.hbm2ddl.auto", "create");
	            setProperty("hibernate.show_sql", "true");
	            setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
	            setProperty("hibernate.c3p0.min_size","5");
				setProperty("hibernate.c3p0.max_size","20");
			    setProperty("hibernate.c3p0.acquire_increment","1");
			    setProperty("hibernate.c3p0.timeout","1800");
			    setProperty("hibernate.c3p0.max_statements","150");
	         }
	      };
	}

   private DataSource getDriverManagerDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName("org.h2.Driver");
      dataSource.setUrl("jdbc:h2:tcp://localhost/~/bookDB");
      dataSource.setUsername("sa");
      dataSource.setPassword("sa");

      return dataSource;
   }


   @Bean
   public HibernateTransactionManager getTransactionManager() {
      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
      transactionManager.setSessionFactory(getSessionFactory().getObject());
      return transactionManager;
   }
}