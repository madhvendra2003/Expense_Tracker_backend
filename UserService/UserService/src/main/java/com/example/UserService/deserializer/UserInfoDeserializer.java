package com.example.UserService.deserializer;

import com.example.UserService.entities.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.sql.SQLOutput;
import java.util.Map;

public class UserInfoDeserializer implements Deserializer<UserInfoDto> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public UserInfoDto deserialize(String arg0, byte[] arg1) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfoDto user = null;
        try {

           user = objectMapper.readValue(arg1, UserInfoDto.class);
        }
        catch(Exception e){
            System.out.println("Exception in deserializing the user info "+e.getMessage());
            e.printStackTrace();
        }
        return user;

    }

    @Override
    public UserInfoDto deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public UserInfoDto deserialize(String topic, Headers headers, ByteBuffer data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
