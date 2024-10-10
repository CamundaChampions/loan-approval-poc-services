package com.gen.poc.loanapproval.service;

import com.gen.poc.loanapproval.constant.enums.TaskStatus;
import com.gen.poc.loanapproval.dto.LoanRequestDTO;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.LoanApprovalTaskRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import com.gen.poc.loanapproval.services.mapper.LoanRequestMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanSubmitService {

    private final WorkflowService workflowService;

    private final LoanRequestMapper loanRequestMapper;

    private final LoanApplicationRepository loanApplicationRepository;

    private final LoanApprovalTaskRepository loanApprovalTaskRepository;

    private final CustomTaskService customTaskService;

    private final RuntimeService runtimeService;


    public String processLoanRequest(String userId, LoanRequestDTO request) {

        LoanApplication loanApplication = loanRequestMapper.toLoanRequestEntityOnCreate(request);
        loanApplication.setStatus(LoanApplicationStatus.CREATED);
        loanApplication.setCustomerId(userId);
        loanApplication = loanApplicationRepository.save(loanApplication);
        Map<String, Object> params = new HashMap<>();
        params.put("isApplicationComplete", true);
        params.put("loan-id", loanApplication.getLoanApplicationId());

        ProcessInstance processInstance = workflowService.createProcessInstance("LOAN_APPROVAL_PROCESS", params);

        loanApplication.setProcessInstanceId(String.valueOf(processInstance.getProcessInstanceId()));
        loanApplicationRepository.save(loanApplication);
        return String.valueOf(loanApplication.getLoanApplicationId());
    }

    public void completeUserTask(String loanApplicationId, String taskName, Map<String, Object> additionalParam) {

        List<LoanApprovalTask> loanApprovalTask = loanApprovalTaskRepository.findByLoanApplicationIdAndTaskCategory(Long.valueOf(loanApplicationId), taskName);

        TaskStatus status = TaskStatus.COMPLETED;
        if (!CollectionUtils.isEmpty(loanApprovalTask) && loanApprovalTask.get(0).getStatus() == TaskStatus.IN_PROGRESS) {
            if ((boolean) additionalParam.get("hasMissingData"))
                status = TaskStatus.REJECTED;

            loanApprovalTask.get(0).setStatus(status);
            loanApprovalTaskRepository.save(loanApprovalTask.get(0));

            Task task = customTaskService.getTask(taskName);
            customTaskService.complete(task.getId(), additionalParam);

        }

    }

    public void acknowledgeDocumentSigning(String loanId, Map<String, Object> additionalParam) {

        runtimeService.createMessageCorrelation("MSGEVNT_SIGNED_DOC_RECIEVED") // Message name
                .processInstanceBusinessKey("MSGEVNT_SIGNED_DOC_RECIEVED") // Correlation key
                .setVariables(additionalParam) // Variables to pass
                .correlate();
    }

}
