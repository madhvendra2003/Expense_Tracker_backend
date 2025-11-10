package com.example.expenseService.service;


import com.example.expenseService.dtos.ExpenseDto;
import com.example.expenseService.entity.ExpenseEntity;
import com.example.expenseService.repository.ExpenseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@NoArgsConstructor
@Data
public class ExpenseService {

    private ExpenseRepository expenseRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, ObjectMapper objectMapper) {
        this.expenseRepository = expenseRepository;
        this.objectMapper = objectMapper;
    }

    public boolean createExpense(ExpenseDto expenseDto){
        setCurrency(expenseDto);
        try{
            log.info("Expense to be saved: " + expenseDto);
            ExpenseEntity expenseEntity = ExpenseEntity.builder()
                    .userId(expenseDto.getUserId())
                    .externalId(expenseDto.getExternalId())
                    .amount(expenseDto.getAmount())
                    .currency(expenseDto.getCurrency())
                    .merchant(expenseDto.getMerchant())
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .build();
            log.info("Expense to be saved: " + expenseEntity);
           expenseRepository.save(expenseEntity);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean UpdateExpense(ExpenseDto expenseDto){
        Optional<ExpenseEntity> expenseFoundOpt = expenseRepository.findByUserIdAndExternalId(expenseDto.getUserId(), expenseDto.getExternalId());
        if (expenseFoundOpt.isEmpty()){
            return false;
        }
        ExpenseEntity expenseEntity = expenseFoundOpt.get();
        expenseEntity.setAmount(Strings.isNotBlank(expenseDto.getAmount())?expenseDto.getAmount():expenseEntity.getAmount());
        expenseEntity.setCurrency(Strings.isNotBlank(expenseDto.getCurrency())? expenseDto.getCurrency() : expenseEntity.getCurrency());
        expenseEntity.setMerchant(Strings.isNotBlank(expenseDto.getMerchant())? expenseDto.getMerchant() : expenseEntity.getMerchant());
        expenseRepository.save(expenseEntity);
        return true;



    }

    public List<ExpenseDto> getAllExpensesByUserId(String userId){
        List<ExpenseEntity> expenseEntities = expenseRepository.findByUserId(userId);
        List<ExpenseDto> result  = new ArrayList<>();
        for(ExpenseEntity expenseEntity : expenseEntities){
            log.info("Fetched Expense Entity: " + expenseEntity);
            ExpenseDto temp = new ExpenseDto();
            temp.setUserId( expenseEntity.getUserId());
            temp.setExternalId(expenseEntity.getExternalId());
            temp.setAmount(expenseEntity.getAmount());
            temp.setCurrency(expenseEntity.getCurrency());
            temp.setMerchant(expenseEntity.getMerchant());
            temp.setCreatedAt(expenseEntity.getCreatedAt().toString());
            result.add(temp);


        }
        return result;

    }

     public List<ExpenseDto> getExpensesByDateRange(String userId, Timestamp startDate, Timestamp endDate){
        List<ExpenseEntity> expenseEntities = expenseRepository.findByUserIdAndCreatedAtBetween(userId, startDate, endDate);
        return expenseEntities.stream()
                .map(expenseEntity -> objectMapper.convertValue(expenseEntity, ExpenseDto.class))
                .toList();
    }



    private void setCurrency(ExpenseDto expenseDto){
        if(Objects.isNull(expenseDto.getCurrency())){
            expenseDto.setCurrency("inr");
        }
    }

}
