package org.example.service;


import org.apache.catalina.User;
import org.example.entities.RefreshToken;
import org.example.entities.userInfo;
import org.example.repository.RefreshTokenRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

   @Autowired
    RefreshTokenRepository refreshTokenRepository;

   @Autowired
    UserRepository userRepository;


   public RefreshToken createRefreshToken(String username){
       userInfo user = userRepository.findByUsername(username);
       RefreshToken refreshToken = RefreshToken.builder()
               .userInfo(user)
               .token(UUID.randomUUID().toString())
               .expiryDate(Instant.now().plusMillis(600000))
               .build();
       return refreshTokenRepository.save(refreshToken);

   }

   public RefreshToken verifyExpiration(RefreshToken token){
       if(token.getExpiryDate().compareTo(Instant.now())<0){
           refreshTokenRepository.delete(token);
              throw new RuntimeException(token.getToken()+ " Refresh token was expired. Please make a new signin request");
       }

       return token;
   }

   public Optional<RefreshToken> findByToken(String token){
       return refreshTokenRepository.findByToken(token);
   }

}
