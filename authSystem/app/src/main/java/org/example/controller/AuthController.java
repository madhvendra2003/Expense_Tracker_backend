package org.example.controller;


import lombok.AllArgsConstructor;
import org.example.entities.RefreshToken;
import org.example.models.UserInfoDto;
import org.example.service.JwtService;
import org.example.service.RefreshTokenService;
import org.example.service.UserDetailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Controller
@AllArgsConstructor
public class AuthController {

    private JwtService jwtService;
    private RefreshTokenService refreshTokenService;
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/auth/v1/signup")
    public ResponseEntity<?> SighUp(@RequestBody UserInfoDto userInfoDto){
        try{
            Boolean isSignedUp = userDetailService.sighnUpUser(userInfoDto);
            if(!isSignedUp){
                return ResponseEntity.badRequest().body("User already exists");
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken = jwtService.GenerateToken(userInfoDto.getUsername());
            return ResponseEntity.ok().body(
                    org.example.Response.JwtResponseDto.builder()
                            .accessToken(jwtToken)
                            .refreshToken(refreshToken.getToken()).build());


        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/auth/v1/ping")
    public ResponseEntity<String> ping() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = userDetailService.getUserByUsername(authentication.getName());
            if(Objects.nonNull(userId)){
                return ResponseEntity.ok(userId);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

}
