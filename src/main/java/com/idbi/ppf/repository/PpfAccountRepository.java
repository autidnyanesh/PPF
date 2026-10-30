package com.idbi.ppf.repository;

import com.idbi.ppf.entity.PpfAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PpfAccountRepository extends JpaRepository<PpfAccount, Long> {
    Optional<PpfAccount> findByAccountNumberIgnoreCase(String accountNumber);
    boolean existsByAccountNumberIgnoreCase(String accountNumber);
    List<PpfAccount> findAllByOrderByIdDesc();
}
