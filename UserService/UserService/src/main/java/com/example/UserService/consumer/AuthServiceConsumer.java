package com.example.UserService.consumer;


import com.example.UserService.entities.UserInfoDto;
import com.example.UserService.repository.UserInfoJPA;
import com.example.UserService.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceConsumer {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;



    @KafkaListener(topics="${spring.kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDto eventData){
        try {
            userService.createOrUpdateUser( eventData);
        }
        catch(Exception e){
            System.out.println("the issue is in AuthServiceConsumer "+ e.getMessage());
            e.printStackTrace();

        }
    }



}
