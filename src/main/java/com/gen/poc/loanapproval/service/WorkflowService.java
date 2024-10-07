package com.gen.poc.loanapproval.service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

@Service
public class WorkflowService {

    @Autowired
    @Qualifier("camundaDataSource") // Specify the Camunda data source
    private DataSource camundaDataSource;

    @Autowired
    private RuntimeService runtimeService;

    // Create a new process instance
    public ProcessInstance createProcessInstance(String processDefinitionKey, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    }

    // Get a process instance by ID
    public ProcessInstance getProcessInstance(String processInstanceId) {
        return runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
    }

    // Delete a process instance by ID
    public void deleteProcessInstance(String processInstanceId, String reason) {
        runtimeService.deleteProcessInstance(processInstanceId, reason);
    }
}
