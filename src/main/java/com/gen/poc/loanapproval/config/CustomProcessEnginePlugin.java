package com.gen.poc.loanapproval.config;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.db.sql.DbSqlSessionFactory;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(Ordering.DEFAULT_ORDER + 1)
public class CustomProcessEnginePlugin implements ProcessEnginePlugin {

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        // Custom configuration can be done here
        log.info("Custom Process Engine Plugin: preInit");
        DbSqlSessionFactory.databaseSpecificTrueConstant.put("h2", "true");
        DbSqlSessionFactory.databaseSpecificTrueConstant.put("h2", "true");
        DbSqlSessionFactory.databaseSpecificBitAnd2.put("h2", ",CAST(");
        DbSqlSessionFactory.databaseSpecificBitAnd3.put("h2", " AS BIGINT))");
        SpringProcessEngineConfiguration configuration = (SpringProcessEngineConfiguration) processEngineConfiguration;
        configuration.setDeploymentName("LOAN_APPROVAL_PROCESS");
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        // Post-init configuration
        log.info("Custom Process Engine Plugin: postInit");
    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }

}
