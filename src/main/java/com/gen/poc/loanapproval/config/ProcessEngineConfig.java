//package com.gen.poc.loanapproval.config;
//
//import org.camunda.bpm.spring.boot.starter.configuration.impl.ProcessEngineConfigurationImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class ProcessEngineConfig {
//
//    @Autowired
//    @Qualifier("camundaDataSource")
//    private DataSource camundaDataSource;
//
//    @Bean
//    @ConditionalOnMissingBean
//    public ProcessEngineConfigurationImpl processEngineConfiguration(DataSourceTransactionManager transactionManager) {
//        ProcessEngineConfigurationImpl configuration = new ProcessEngineConfigurationImpl();
//        configuration.setDataSource(camundaDataSource);
//        configuration.setTransactionManager(transactionManager);
//        configuration.setDatabaseSchemaUpdate("true"); // Change based on your needs
//        configuration.setHistory("full"); // Set history level based on your requirements
//        configuration.setJobExecutorActivate(true); // Activate the job executor
//        return configuration;
//    }
//
//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(camundaDataSource);
//    }
//}
