package com.example.expenseService.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name ="external_Id")
    private String externalId;

    @Column(name="user_id")
    private String userId;

    @Column(name="amount")
    private String amount;


    @Column(name="merchant")
    private String merchant;

    @Column(name = "currency")
    private String currency;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @PrePersist
    @PreUpdate
    private void generateExternalID(){
        if (this.externalId == null || this.externalId.isEmpty()) {
            this.externalId = java.util.UUID.randomUUID().toString();
        }
    }





}
