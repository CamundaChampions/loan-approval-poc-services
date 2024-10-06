package com.gen.poc.loanapproval.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Risk_Assessment")
public class RiskAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "creadit_score", nullable = false)
    private Integer creditScore;

    @Column(name = "credit_debts", nullable = false)
    private Integer creditDebts;

    @Column(name = "category", nullable = false, length = 20)
    private String category;

    @Column(name = "reason", length = 256)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "loan_request_id", nullable = false)
    private LoanRequest loanRequest;

    // Getters and Setters
}
