package com.gen.poc.loanapproval.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CamundaDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.camunda")
    public DataSourceProperties camundaDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource camundaDataSource() {
        return camundaDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
