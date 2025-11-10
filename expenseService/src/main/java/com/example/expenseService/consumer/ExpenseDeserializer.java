package com.example.expenseService.consumer;

import com.example.expenseService.dtos.ExpenseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class ExpenseDeserializer implements Deserializer<ExpenseDto> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public ExpenseDto deserialize(String arg0, byte[] arg1) {


        ObjectMapper objectMapper = new ObjectMapper();
        ExpenseDto expense = null;
        try{

            System.out.println("Deserialized expense: " + expense);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return expense;

    }

    @Override
    public ExpenseDto deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public ExpenseDto deserialize(String topic, Headers headers, ByteBuffer data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
