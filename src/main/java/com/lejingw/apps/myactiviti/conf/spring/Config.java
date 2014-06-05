package com.lejingw.apps.myactiviti.conf.spring;

import javax.sql.DataSource;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.annotations.AbstractActivitiConfigurer;
import org.activiti.spring.annotations.EnableActiviti;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @EnableActiviti
 * 1.a default in-memory H2 database, with auto-schema upgrade enabled.
 * 
 * 2.a simple DataSourceTransactionManager
 * 
 * 3.a default SpringJobExecutor
 * 4.a scanner for bpmn20.xml files residing in the processes/ folder.
 */
@Configuration
@EnableActiviti
@EnableTransactionManagement(proxyTargetClass = true)
class JPAConfiguration {

//    @Bean
//    public OpenJpaVendorAdapter openJpaVendorAdapter() {
//        OpenJpaVendorAdapter openJpaVendorAdapter = new OpenJpaVendorAdapter();
//        openJpaVendorAdapter.setDatabasePlatform(H2Dictionary.class.getName());
//        return openJpaVendorAdapter;
//    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername("sa");
        basicDataSource.setUrl("jdbc:h2:mem:activiti");
        basicDataSource.setDefaultAutoCommit(false);
        basicDataSource.setDriverClassName(org.h2.Driver.class.getName());
        basicDataSource.setPassword("");
        return basicDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
        OpenJpaVendorAdapter openJpaVendorAdapter, DataSource ds) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPersistenceXmlLocation("classpath:/org/activiti/spring/test/jpa/custom-persistence.xml");
        emf.setJpaVendorAdapter(openJpaVendorAdapter);
        emf.setDataSource(ds);
        return emf;
    }

//    @Bean
//    public PlatformTransactionManager jpaTransactionManager(
//        EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//
//    @Bean
//    public AbstractActivitiConfigurer abstractActivitiConfigurer(
//        final EntityManagerFactory emf,
//        final PlatformTransactionManager transactionManager) {
//
//        return new AbstractActivitiConfigurer() {
//
//            @Override
//            public void postProcessSpringProcessEngineConfiguration(SpringProcessEngineConfiguration engine) {
//                engine.setTransactionManager(transactionManager);
//                engine.setJpaEntityManagerFactory(emf);
//                engine.setJpaHandleTransaction(false);
//                engine.setJobExecutorActivate(false);
//                engine.setJpaCloseEntityManager(false);
//                engine.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//            }
//        };
//    }
//
//    //A random bean
//    @Bean
//    public LoanRequestBean loanRequestBean() {
//        return new LoanRequestBean();
//    }
}