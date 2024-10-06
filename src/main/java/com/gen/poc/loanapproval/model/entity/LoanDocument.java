package com.gen.poc.loanapproval.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Loan_Documents")
public class LoanDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "document_type", nullable = false, length = 25)
    private String documentType;

    @Column(name = "fileName", nullable = false, length = 25)
    private String fileName;

    @Column(name = "File_Path", nullable = false, length = 80)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "loan_request_id", nullable = false)
    private LoanRequest loanRequest;

    // Getters and Setters
}
