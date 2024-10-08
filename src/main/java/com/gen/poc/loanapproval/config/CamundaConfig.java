package com.gen.poc.loanapproval.config;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaConfig {

    @Bean
    public CustomProcessEnginePlugin customProcessEnginePlugin() {
        return new CustomProcessEnginePlugin();
    }
}
