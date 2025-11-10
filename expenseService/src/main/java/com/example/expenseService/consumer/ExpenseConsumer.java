package com.example.expenseService.consumer;


import com.example.expenseService.dtos.ExpenseDto;
import com.example.expenseService.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ExpenseConsumer {

    private ExpenseService expenseService;

    @Autowired
    public ExpenseConsumer(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


     @KafkaListener(topics="${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void Listen(ExpenseDto expenseDto){
        try{
            expenseDto.setCreatedAt(String.valueOf(System.currentTimeMillis()));

            expenseService.createExpense(expenseDto);
            // try expense service over here
        } catch (Exception e) {
            System.out.println("the issue is in ExpenseConsumer "+ e.getMessage());
            e.printStackTrace();
        }
     }



}
