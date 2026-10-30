package com.idbi.ppf.repository;

import com.idbi.ppf.entity.PpfTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PpfTransactionRepository extends JpaRepository<PpfTransaction, Long> {
    List<PpfTransaction> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}
