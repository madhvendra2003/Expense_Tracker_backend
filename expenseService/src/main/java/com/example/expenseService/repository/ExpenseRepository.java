package com.example.expenseService.repository;

import com.example.expenseService.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    List<ExpenseEntity> findByUserId(String userId);
    List<ExpenseEntity> findByUserIdAndCreatedAtBetween(String userId, Timestamp startDate, Timestamp endDate);
    Optional<ExpenseEntity> findByUserIdAndExternalId(String userId, String externalId);

}
