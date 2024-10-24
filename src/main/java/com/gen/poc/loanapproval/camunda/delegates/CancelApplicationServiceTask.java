package com.gen.poc.loanapproval.camunda.delegates;


import com.gen.poc.loanapproval.enums.LoanApplicationStatus;

import com.gen.poc.loanapproval.enums.TaskStatus;
import com.gen.poc.loanapproval.exception.LoanNotFoundException;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.LoanApprovalTaskRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component("cancelApplicationServiceTask")
@Slf4j
@RequiredArgsConstructor
public class CancelApplicationServiceTask implements BaseDelegate {

    private final LoanApplicationRepository loanApplicationRepository;

    private final LoanApprovalTaskRepository loanApprovalTaskRepository;

    /**
     * @param delegateExecution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String cancelType = getStringValueByKey(delegateExecution, "cancelType");
        LoanApplicationStatus loanApplicationStatus = null;
        if (StringUtils.equals(cancelType, "Auto Cancelled")) {
            loanApplicationStatus = LoanApplicationStatus.AUTO_CANCELLED;
        }
        if (StringUtils.equals(cancelType, "Cancelled")) {
            loanApplicationStatus = LoanApplicationStatus.CANCELLED;
        }
        if (StringUtils.equals(cancelType, "Rejected")) {
            loanApplicationStatus = LoanApplicationStatus.REJECTED;
        }
        Long loanApplicationId = (Long) getValueByKey(delegateExecution, "loan-id");
        cancelLoanApplication(loanApplicationId, loanApplicationStatus);
    }


    private void cancelLoanApplication(long loanApplicationId, LoanApplicationStatus status) {
        Optional<LoanApplication> loanApplicationOptional = loanApplicationRepository.findById(loanApplicationId);
        if (loanApplicationOptional.isEmpty())
            throw new LoanNotFoundException(loanApplicationId);


        LoanApplication loanApplication = loanApplicationOptional.get();

        loanApplication.setStatus(status);
        loanApplicationRepository.save(loanApplication);

        List<LoanApprovalTask> loanApprovalTaskList = loanApprovalTaskRepository.findByLoanApplicationIdAndStatus(loanApplicationId, TaskStatus.IN_PROGRESS);
        if (!CollectionUtils.isEmpty(loanApprovalTaskList)) {
            loanApprovalTaskList.forEach(task -> task.setStatus(TaskStatus.CANCELLED));
            loanApprovalTaskRepository.saveAll(loanApprovalTaskList);
        }
    }
}
