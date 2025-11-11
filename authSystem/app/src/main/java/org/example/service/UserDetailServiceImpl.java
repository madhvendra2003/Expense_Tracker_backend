package org.example.service;


import lombok.AllArgsConstructor;
import org.example.entities.userInfo;
import org.example.eventProducer.UserInfoProducer;
import org.example.models.UserInfoDto;
import org.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoProducer userInfoProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userInfo user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found with name "+username);
    }
        return new CustomUserDetails(user);
    }

    public userInfo checkIfUserExists(String username){
        return userRepository.findByUsername(username);
    }

    public Boolean sighnUpUser(UserInfoDto user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(checkIfUserExists(user.getUsername())==null){
            userInfo userInfo = new userInfo();
            userInfo.setUsername(user.getUsername());
            userInfo.setPassword(user.getPassword());
            userInfo.setRoles(new HashSet<>());


            userInfo temp = userRepository.save(userInfo);

             user.setUserId(temp.getUserId());

            //sending the event to kafka topic can be done here
            userInfoProducer.sendEventToKafka(user);
            return true;
        }
        return false;
    }


    public String getUserByUsername(String username) {
        userInfo user = userRepository.findByUsername(username);
        if(user!=null){
            return Integer.toString(user.getUserId()) ;
        }
        return null;
    }
}
