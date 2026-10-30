package com.idbi.ppf.service.impl;

import com.idbi.ppf.dto.PpfDtos;
import com.idbi.ppf.entity.PpfAccount;
import com.idbi.ppf.entity.PpfAccountStatus;
import com.idbi.ppf.entity.PpfTransaction;
import com.idbi.ppf.entity.PpfTransactionType;
import com.idbi.ppf.mapper.PpfMapper;
import com.idbi.ppf.repository.PpfAccountRepository;
import com.idbi.ppf.repository.PpfTransactionRepository;
import com.idbi.ppf.service.PpfAccountService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class PpfAccountServiceImpl implements PpfAccountService {
    private final PpfAccountRepository accounts;
    private final PpfTransactionRepository transactions;
    private final PpfMapper mapper;

    public PpfAccountServiceImpl(PpfAccountRepository accounts, PpfTransactionRepository transactions, PpfMapper mapper) {
        this.accounts = accounts;
        this.transactions = transactions;
        this.mapper = mapper;
    }

    @Override
    public PpfDtos.AccountSummary open(PpfDtos.OpenAccountRequest request) {
        if (accounts.existsByAccountNumberIgnoreCase(request.accountNumber()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "PPF account number already exists");
        return mapper.toSummary(accounts.save(new PpfAccount(request.accountNumber(), request.customerName(),
                request.customerId(), request.openingBalance(), request.openedOn())));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PpfDtos.AccountSummary> list() {
        return accounts.findAllByOrderByIdDesc().stream().map(mapper::toSummary).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PpfDtos.AccountDetails get(long id) {
        PpfAccount account = findAccount(id);
        return mapper.toDetails(account, transactions.findByAccountIdOrderByCreatedAtDesc(id));
    }

    @Override
    public PpfDtos.TransactionSummary contribute(long id, PpfDtos.MoneyRequest request) {
        PpfAccount account = activeAccount(id);
        account.credit(request.amount());
        accounts.flush();
        return mapper.toSummary(transactions.save(new PpfTransaction(account, PpfTransactionType.CONTRIBUTION,
                request.amount(), request.remarks())));
    }

    @Override
    public PpfDtos.TransactionSummary withdraw(long id, PpfDtos.MoneyRequest request) {
        PpfAccount account = activeAccount(id);
        if (account.getBalance().compareTo(request.amount()) < 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Withdrawal exceeds available balance");
        account.debit(request.amount());
        accounts.flush();
        return mapper.toSummary(transactions.save(new PpfTransaction(account, PpfTransactionType.WITHDRAWAL,
                request.amount(), request.remarks())));
    }

    @Override
    public PpfDtos.AccountSummary close(long id) {
        PpfAccount account = activeAccount(id);
        account.close();
        return mapper.toSummary(account);
    }

    private PpfAccount activeAccount(long id) {
        PpfAccount account = findAccount(id);
        if (account.getStatus() != PpfAccountStatus.ACTIVE)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "PPF account is closed");
        return account;
    }

    private PpfAccount findAccount(long id) {
        return accounts.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PPF account not found"));
    }
}
