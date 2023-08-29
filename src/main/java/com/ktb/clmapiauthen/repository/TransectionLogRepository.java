package com.ktb.clmapiauthen.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ktb.clmapiauthen.entity.TransactionLog;
import org.springframework.stereotype.Repository;

@Repository
public interface TransectionLogRepository extends JpaRepository<TransactionLog, Integer>{
        List<TransactionLog> findByToken(String token);
}
