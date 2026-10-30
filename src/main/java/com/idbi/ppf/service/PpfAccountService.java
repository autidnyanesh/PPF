package com.idbi.ppf.service;

import com.idbi.ppf.dto.PpfDtos;
import java.util.List;

public interface PpfAccountService {
    PpfDtos.AccountSummary open(PpfDtos.OpenAccountRequest request);
    List<PpfDtos.AccountSummary> list();
    PpfDtos.AccountDetails get(long id);
    PpfDtos.TransactionSummary contribute(long id, PpfDtos.MoneyRequest request);
    PpfDtos.TransactionSummary withdraw(long id, PpfDtos.MoneyRequest request);
    PpfDtos.AccountSummary close(long id);
}
