package com.idbi.ppf.mapper;

import com.idbi.ppf.dto.PpfDtos;
import com.idbi.ppf.entity.PpfAccount;
import com.idbi.ppf.entity.PpfTransaction;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PpfMapper {
    public PpfDtos.AccountSummary toSummary(PpfAccount account) {
        return new PpfDtos.AccountSummary(account.getId(), account.getAccountNumber(), account.getCustomerName(),
                account.getCustomerId(), account.getBalance(), account.getStatus(), account.getOpenedOn());
    }

    public PpfDtos.TransactionSummary toSummary(PpfTransaction transaction) {
        return new PpfDtos.TransactionSummary(transaction.getId(), transaction.getType(), transaction.getAmount(),
                transaction.getRemarks(), transaction.getCreatedAt());
    }

    public PpfDtos.AccountDetails toDetails(PpfAccount account, List<PpfTransaction> transactions) {
        return new PpfDtos.AccountDetails(toSummary(account), transactions.stream().map(this::toSummary).toList());
    }
}
