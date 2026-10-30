package com.idbi.ppf.dto;

import com.idbi.ppf.entity.PpfAccountStatus;
import com.idbi.ppf.entity.PpfTransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public final class PpfDtos {
    private PpfDtos() {}

    public record OpenAccountRequest(@NotBlank @Size(max = 30) String accountNumber,
            @NotBlank @Size(max = 160) String customerName, @Size(max = 50) String customerId,
            @NotNull @DecimalMin("0.00") BigDecimal openingBalance, LocalDate openedOn) {}
    public record MoneyRequest(@NotNull @DecimalMin(value = "0.01") BigDecimal amount,
            @Size(max = 300) String remarks) {}
    public record AccountSummary(Long id, String accountNumber, String customerName, String customerId,
            BigDecimal balance, PpfAccountStatus status, LocalDate openedOn) {}
    public record TransactionSummary(Long id, PpfTransactionType type, BigDecimal amount,
            String remarks, Instant createdAt) {}
    public record AccountDetails(AccountSummary account, List<TransactionSummary> transactions) {}
}
