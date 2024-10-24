package com.gen.poc.loanapproval.service;

import com.gen.poc.loanapproval.constant.AppConstants;
import com.gen.poc.loanapproval.dto.LoanRequestDTO;
import com.gen.poc.loanapproval.dto.LoanSummaryDto;
import com.gen.poc.loanapproval.dto.LoanSummaryListResponse;
import com.gen.poc.loanapproval.dto.LoanSummaryResponse;
import com.gen.poc.loanapproval.enums.*;
import com.gen.poc.loanapproval.exception.LoanNotFoundException;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.LoanApprovalTaskRepository;
import com.gen.poc.loanapproval.repository.LoanSummaryRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import com.gen.poc.loanapproval.repository.entity.LoanSummary;
import com.gen.poc.loanapproval.services.mapper.LoanRequestMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

import static com.gen.poc.loanapproval.constant.AppConstants.EVNTSTARTMSGEVENT_CANCELLATION;

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

    private final LoanSummaryRepository loanSummaryRepository;


    public String processLoanRequest(String userId, LoanRequestDTO request) {

        LoanApplication loanApplication = loanRequestMapper.toLoanRequestEntityOnCreate(request);
        loanApplication.setStatus(LoanApplicationStatus.CREATED);
        loanApplication.setCustomerId(userId);
        loanApplication = loanApplicationRepository.save(loanApplication);
        Map<String, Object> params = new HashMap<>();
        params.put("loan-id", loanApplication.getLoanApplicationId());
        params.put("userId", userId);
        // params.put("hasMissingData", false);
        params.put(EVNTSTARTMSGEVENT_CANCELLATION, EVNTSTARTMSGEVENT_CANCELLATION.concat("-").concat(String.valueOf(loanApplication.getLoanApplicationId())));

        ProcessInstance processInstance = workflowService.createProcessInstance("LOAN_APPROVAL_PROCESS", params);

        loanApplication.setProcessInstanceId(String.valueOf(processInstance.getProcessInstanceId()));
        loanApplicationRepository.save(loanApplication);
        return String.valueOf(loanApplication.getLoanApplicationId());
    }

    public void completeUserTask(Long loanApplicationId, String userId, UserRoleAndUserList userRole, Decision decision, String comments) {

        String taskId;
        if (userRole == UserRoleAndUserList.FINANCIAL_ASSESSMENT_MANAGER)
            taskId = "FM-".concat(String.valueOf(loanApplicationId));
        else
            taskId = "RM-".concat(String.valueOf(loanApplicationId));

        log.info("Task is - {}", taskId);
        LoanApprovalTask loanApprovalTask = loanApprovalTaskRepository.findPendingApprovalTask(taskId);
        if (loanApprovalTask == null)
            throw new LoanNotFoundException(loanApplicationId);

        Map<String, Object> variable = new HashMap<>();


        if (userRole == UserRoleAndUserList.FINANCIAL_ASSESSMENT_MANAGER)
            variable.put("hasMissingData", decision == Decision.REJECT);
        else
            variable.put("decision", decision.name());

        TaskStatus status = TaskStatus.REJECTED;
        if (decision == Decision.APPROVE) {
            status = TaskStatus.COMPLETED;
        }

        loanApprovalTask.setStatus(status);
        loanApprovalTaskRepository.save(loanApprovalTask);
        customTaskService.complete(loanApprovalTask.getTaskInstanceId(), variable);
    }

    public void acknowledgeDocumentSigning(String loanId, Map<String, Object> additionalParam) {
        LoanApplication loanApplication = findLoanApplicationById(Long.valueOf(loanId));
        runtimeService.createMessageCorrelation(AppConstants.MSGEVNT_SIGNED_DOC_RECEIVED)// Message name
                .processInstanceBusinessKey(String.format(AppConstants.DOC_SIGN_CORRELATION_KEY, loanApplication.getProcessInstanceId())) // Correlation key
                .setVariables(additionalParam) // Variables to pass
                .correlate();
    }

    public void acknowledgeDocumentReAssessment(String loanId, Map<String, Object> additionalParam) {
        LoanApplication loanApplication = findLoanApplicationById(Long.valueOf(loanId));
        runtimeService.createMessageCorrelation(AppConstants.MSGEVNT_AKNOWLEDGE_MISSING_DOC_PROVIDED)// Message name
                .processInstanceBusinessKey(String.format(AppConstants.MISSING_DOC_CORRELATION_KEY, loanApplication.getProcessInstanceId())) // Correlation key
                .setVariables(additionalParam) // Variables to pass
                .correlate();

    }

    public void updateDocumentDetails(String loanId, Map<String, Object> additionalParam, Optional<Long> amountOpt,
                                      Optional<Integer> termOpt) {
        LoanApplication loanApplication = findLoanApplicationById(Long.valueOf(loanId));

        amountOpt.ifPresent(amount -> {
            if (amount > 0) {
                loanApplication.setAmount(BigDecimal.valueOf(amount));
            }
        });
        termOpt.ifPresent(term -> {
            if (term > 0) {
                loanApplication.setTerm(term);
            }
        });
        loanApplicationRepository.flush();

        // validate and verify if application is complete now
        if (ObjectUtils.allNotNull(loanApplication.getAmount(), loanApplication.getLoanCategory(), loanApplication.getTerm())
                && loanApplication.getAmount().longValue() > 0 && loanApplication.getTerm() > 0) {
            additionalParam.put("isApplicationComplete", true);
            runtimeService.createMessageCorrelation(AppConstants.MSGEVNT_MISSING_APP_DATA_RECIEVED_AKNWLG)// Message name
                    .processInstanceBusinessKey(String.format(AppConstants.APP_UPDATED_CORRELATION_KEY, loanApplication.getProcessInstanceId())) // Correlation key
                    .setVariables(additionalParam) // Variables to pass
                    .correlate();
        } else {
            log.info("Mandatory data is invalid or not provided cannot proceed");
        }
    }

    private LoanApplication findLoanApplicationById(Long loanApplicationId) {
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        if (loanApplication.isEmpty())
            throw new LoanNotFoundException(loanApplicationId);

        return loanApplication.get();

    }

    public LoanSummaryListResponse findAllUserItems(String user, boolean includeClosedApplication) {

        LoanSummaryListResponse response = new LoanSummaryListResponse();
        UserRoleAndUserList userRole = UserRoleAndUserList.getUserRole(user);
        List<LoanSummaryDto> loanSummaries = findAllTaskByUser(user, userRole, includeClosedApplication);
        response.setLoanSummaryList(loanSummaries);
        response.setAllowToCreateLoan(userRole == UserRoleAndUserList.APPLICANT && CollectionUtils.isEmpty(loanSummaries));
        return response;

    }

    public List<LoanSummaryDto> findAllTaskByUser(String user, UserRoleAndUserList userRole, boolean includeClosedApplication) {
        List<LoanSummary> loanSummaries = new ArrayList<>();
        if (userRole == UserRoleAndUserList.FINANCIAL_ASSESSMENT_MANAGER)
            loanSummaries = loanSummaryRepository.getPendingApprovalTaskDetailsByTaskCategory(ApprovalCategory.FINANCIAL_ASSESSMENT_MANAGER.name(),
                    LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL.name());
        else if (userRole == UserRoleAndUserList.RISK_ASSESSMENT_MANAGER) {
            loanSummaries = loanSummaryRepository.getPendingApprovalTaskDetailsByTaskCategory(ApprovalCategory.RISK_ASSESSMENT_MANAGER.name(),
                    LoanApplicationStatus.PENDING_RISK_ASSESSMENT_MANAGER_APPROVAL.name());
        } else if (userRole == UserRoleAndUserList.APPLICANT) {
            loanSummaries = loanSummaryRepository.getInProcessLoanApplicationItemsOfApplicant(user, includeClosedApplication);
        }

        return loanRequestMapper.mapTo(loanSummaries);

    }

    public LoanSummaryResponse findLoanDetailsByIdAndUser(Long loanId, String userId) {
        UserRoleAndUserList userRole = UserRoleAndUserList.getUserRole(userId);
        LoanSummary loanSummary = new LoanSummary();
        if (userRole == UserRoleAndUserList.FINANCIAL_ASSESSMENT_MANAGER)
            loanSummary = loanSummaryRepository.getPendingApprovalTaskDetailsByTaskCategoryAndLoanId(ApprovalCategory.FINANCIAL_ASSESSMENT_MANAGER.name(),
                    LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL.name(), loanId);
        else if (userRole == UserRoleAndUserList.RISK_ASSESSMENT_MANAGER) {
            loanSummary = loanSummaryRepository.getPendingApprovalTaskDetailsByTaskCategoryAndLoanId(ApprovalCategory.RISK_ASSESSMENT_MANAGER.name(),
                    LoanApplicationStatus.PENDING_RISK_ASSESSMENT_MANAGER_APPROVAL.name(), loanId);
        } else if (userRole == UserRoleAndUserList.APPLICANT) {
            loanSummary = loanSummaryRepository.getInProcessLoanApplicationItemsOfApplicantAndLoanId(userId, loanId);
        }
        if (loanSummary == null)
            throw new LoanNotFoundException(loanId);

        LoanSummaryResponse response = loanRequestMapper.mapToResponse(loanSummary);
        response.setPossibleActivities(PossibleActivity.getPossibleActivityByRoleAndStatus(userRole, response.getStatusCode()));
        return response;
    }

    public void cancelLoan(Long loanId, String userId) {

        LoanSummary loanSummary = loanSummaryRepository.getInProcessLoanApplicationItemsOfApplicantAndLoanId(userId, loanId);
        if (loanSummary == null || List.of(LoanApplicationStatus.APPROVE_AND_DISBURSED,
                        LoanApplicationStatus.CANCELLED,
                        LoanApplicationStatus.AUTO_CANCELLED,
                        LoanApplicationStatus.REJECTED)
                .contains(loanSummary.getStatusCode()))
            throw new LoanNotFoundException(loanId);

        runtimeService.createMessageCorrelation(EVNTSTARTMSGEVENT_CANCELLATION)// Message name
                .processInstanceBusinessKey(EVNTSTARTMSGEVENT_CANCELLATION.concat("-").concat(String.valueOf(loanId)) )// Correlation key
                .setVariables(new HashMap<>()) // Variables to pass
                .correlate();
    }


}




