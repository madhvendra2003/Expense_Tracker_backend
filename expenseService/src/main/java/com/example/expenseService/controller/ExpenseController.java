package com.example.expenseService.controller;

import com.example.expenseService.dtos.ExpenseDto;
import com.example.expenseService.service.ExpenseService;
import io.micrometer.common.lang.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/expense/v1")
public class ExpenseController {
    
    private final ExpenseService expenseService;
    
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    
    @GetMapping("/getExpenses")
    public ResponseEntity<List<ExpenseDto>> getExpense(@RequestHeader(value = "X-User-ID") @NonNull String userId) {
        try {
            System.out.println("Fetching expenses for userId: " + userId);
            List<ExpenseDto> expenses = expenseService.getAllExpensesByUserId(userId);
            return ResponseEntity.ok(expenses);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createExpense(@RequestBody ExpenseDto expenseDto) {
        try {
            boolean created = expenseService.createExpense(expenseDto);
            if (created) {
                return ResponseEntity.ok("Expense created successfully");
            }
            return ResponseEntity.badRequest().body("Failed to create expense");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error creating expense: " + ex.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateExpense(@RequestBody ExpenseDto expenseDto) {
        try {
            boolean updated = expenseService.UpdateExpense(expenseDto);
            if (updated) {
                return ResponseEntity.ok("Expense updated successfully");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error updating expense: " + ex.getMessage());
        }
    }

    @GetMapping("/byDateRange")
    public ResponseEntity<List<ExpenseDto>> getExpensesByDateRange(

            @Header("X-User-ID") String userId,
            @RequestParam Timestamp startDate,
            @RequestParam Timestamp endDate) {
        try {
            List<ExpenseDto> expenses = expenseService.getExpensesByDateRange(userId, startDate, endDate);
            return ResponseEntity.ok(expenses);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
