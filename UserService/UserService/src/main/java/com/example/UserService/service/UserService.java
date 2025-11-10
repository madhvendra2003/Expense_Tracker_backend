package com.example.UserService.service;

import com.example.UserService.entities.UserInfo;
import com.example.UserService.entities.UserInfoDto;
import com.example.UserService.repository.UserInfoJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserInfoJPA userRepository;

    public UserInfoDto createOrUpdateUser(UserInfoDto userInfoDto) {
        Optional<UserInfo> existingUserOpt = Optional.ofNullable(
                userRepository.findByUserId(userInfoDto.getUserId())
        );

        UserInfo userToSave;

        if (existingUserOpt.isPresent()) {
            userToSave = existingUserOpt.get();

            userToSave.setFirstName(userInfoDto.getFirstName());
            userToSave.setLastName(userInfoDto.getLastName());
            userToSave.setPhonenumber(userInfoDto.getPhonenumber());
            userToSave.setEmail(userInfoDto.getEmail());
            userToSave.setProfilePic(userInfoDto.getProfilePic());
        } else {
            userToSave = userInfoDto.transformToUserInfo();
        }

        UserInfo savedUser = userRepository.save(userToSave);

        return new UserInfoDto(
                savedUser.getUserId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getPhonenumber(),
                savedUser.getEmail(),
                savedUser.getProfilePic()
        );
    }

    public UserInfoDto getUser(UserInfoDto userInfoDto) throws Exception {
        Optional<UserInfo> userInfoOpt = Optional.ofNullable(
                userRepository.findByUserId(userInfoDto.getUserId())
        );

        UserInfo userInfo = userInfoOpt
                .orElseThrow(() -> new Exception("User not found with ID: " + userInfoDto.getUserId()));

        return new UserInfoDto(
                userInfo.getUserId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhonenumber(),
                userInfo.getEmail(),
                userInfo.getProfilePic()
        );
    }

    public UserInfoDto getUser(String userId) throws Exception {
        Optional<UserInfo> userInfoOpt = Optional.ofNullable(
                userRepository.findByUserId(userId)
        );

        UserInfo userInfo = userInfoOpt
                .orElseThrow(() -> new Exception("User not found with ID: " + userId));

        return new UserInfoDto(
                userInfo.getUserId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhonenumber(),
                userInfo.getEmail(),
                userInfo.getProfilePic()
        );
    }
}