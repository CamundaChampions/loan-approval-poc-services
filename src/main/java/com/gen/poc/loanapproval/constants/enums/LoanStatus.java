package com.gen.poc.loanapproval.constants.enums;

import lombok.Getter;

@Getter
public enum LoanStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    DISBURSED("Disbursed"),
    COMPLETED("Completed"),
    DEFAULTED("Defaulted");

    private final String displayName;

    LoanStatus(String displayName) {
        this.displayName = displayName;
    }

}
