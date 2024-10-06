package com.gen.poc.loanapproval.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cash_deposit_collateral")
public class CashDepositCollateral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "loan_request_id", nullable = false)
    private LoanRequest loanRequest;

    @Column(name = "bank_name", nullable = false, length = 50)
    private String bankName;

    @Column(name = "account_number", nullable = false, length = 50)
    private String accountNumber;

    @Column(name = "amount_balance", nullable = false)
    private Integer amountBalance;

    // Getters and Setters
}
