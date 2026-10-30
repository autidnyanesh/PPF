package com.idbi.ppf.controller;

import com.idbi.ppf.dto.PpfDtos;
import com.idbi.ppf.service.PpfAccountService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ppf/accounts")
public class PpfAccountController {
    private final PpfAccountService service;

    public PpfAccountController(PpfAccountService service) { this.service = service; }

    @GetMapping
    public List<PpfDtos.AccountSummary> list() { return service.list(); }

    @GetMapping("/{id}")
    public PpfDtos.AccountDetails get(@PathVariable long id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PpfDtos.AccountSummary open(@Valid @RequestBody PpfDtos.OpenAccountRequest request) {
        return service.open(request);
    }

    @PostMapping("/{id}/contributions")
    @ResponseStatus(HttpStatus.CREATED)
    public PpfDtos.TransactionSummary contribute(@PathVariable long id,
            @Valid @RequestBody PpfDtos.MoneyRequest request) {
        return service.contribute(id, request);
    }

    @PostMapping("/{id}/withdrawals")
    @ResponseStatus(HttpStatus.CREATED)
    public PpfDtos.TransactionSummary withdraw(@PathVariable long id,
            @Valid @RequestBody PpfDtos.MoneyRequest request) {
        return service.withdraw(id, request);
    }

    @PostMapping("/{id}/close")
    public PpfDtos.AccountSummary close(@PathVariable long id) { return service.close(id); }
}
