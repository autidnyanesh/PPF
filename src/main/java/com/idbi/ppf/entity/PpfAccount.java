package com.idbi.ppf.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PPF_ACCOUNTS")
public class PpfAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ppf_accounts_seq")
    @SequenceGenerator(name = "ppf_accounts_seq", sequenceName = "PPF_ACCOUNTS_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ACCOUNT_NUMBER", nullable = false, unique = true, length = 30)
    private String accountNumber;

    @Column(name = "CUSTOMER_NAME", nullable = false, length = 160)
    private String customerName;

    @Column(name = "CUSTOMER_ID", length = 50)
    private String customerId;

    @Column(name = "BALANCE", nullable = false, precision = 18, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private PpfAccountStatus status;

    @Column(name = "OPENED_ON", nullable = false)
    private LocalDate openedOn;

    @Version
    @Column(name = "VERSION_NUMBER", nullable = false)
    private long version;

    protected PpfAccount() {}

    public PpfAccount(String accountNumber, String customerName, String customerId, BigDecimal openingBalance,
            LocalDate openedOn) {
        this.accountNumber = accountNumber.trim();
        this.customerName = customerName.trim();
        this.customerId = customerId == null || customerId.isBlank() ? null : customerId.trim();
        this.balance = openingBalance;
        this.openedOn = openedOn == null ? LocalDate.now() : openedOn;
        this.status = PpfAccountStatus.ACTIVE;
    }

    @PrePersist
    void initializeBalance() {
        if (balance == null) balance = BigDecimal.ZERO;
    }

    public Long getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public String getCustomerName() { return customerName; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getBalance() { return balance; }
    public PpfAccountStatus getStatus() { return status; }
    public LocalDate getOpenedOn() { return openedOn; }
    public long getVersion() { return version; }

    public void credit(BigDecimal amount) { this.balance = this.balance.add(amount); }
    public void debit(BigDecimal amount) { this.balance = this.balance.subtract(amount); }
    public void close() { this.status = PpfAccountStatus.CLOSED; }
}
