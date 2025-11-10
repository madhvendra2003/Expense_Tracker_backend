package com.example.UserService.repository;


import com.example.UserService.entities.UserInfo;
import com.example.UserService.entities.UserInfoDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoJPA extends CrudRepository<UserInfo, String> {
    UserInfo findByUserId(String userId);
}
