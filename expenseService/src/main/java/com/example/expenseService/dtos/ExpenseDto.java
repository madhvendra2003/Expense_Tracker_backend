package com.example.expenseService.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDto {



    private String externalId;

    @JsonProperty(value = "amount")
    @NonNull
    private String amount;

    @JsonProperty(value = "user_id")
    @NonNull
    private String userId;

    @JsonProperty(value = "merchant")
    private String merchant;

    @JsonProperty(value="currency")
    private String currency;

    @JsonProperty(value = "created_at")
    private String createdAt;


    public ExpenseDto (String json){
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            ExpenseDto dto = mapper.readValue(json, ExpenseDto.class);
            this.externalId = dto.externalId;
            this.amount = dto.amount;
            this.userId = dto.userId;
            this.merchant = dto.merchant;
            this.currency = dto.currency;
            this.createdAt = dto.createdAt;

        }catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
