package com.idbi.ppf.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "PPF_TRANSACTIONS")
public class PpfTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ppf_transactions_seq")
    @SequenceGenerator(name = "ppf_transactions_seq", sequenceName = "PPF_TRANSACTIONS_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private PpfAccount account;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSACTION_TYPE", nullable = false, length = 20)
    private PpfTransactionType type;

    @Column(name = "AMOUNT", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "REMARKS", length = 300)
    private String remarks;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

    protected PpfTransaction() {}

    public PpfTransaction(PpfAccount account, PpfTransactionType type, BigDecimal amount, String remarks) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.remarks = remarks == null || remarks.isBlank() ? null : remarks.trim();
    }

    @PrePersist
    void onCreate() { if (createdAt == null) createdAt = Instant.now(); }

    public Long getId() { return id; }
    public PpfAccount getAccount() { return account; }
    public PpfTransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getRemarks() { return remarks; }
    public Instant getCreatedAt() { return createdAt; }
}
