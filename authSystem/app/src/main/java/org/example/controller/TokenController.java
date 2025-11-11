package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.Response.JwtResponseDto;
import org.example.entities.RefreshToken;
import org.example.request.AuthRequestDto;
import org.example.request.RefreshTokenRequest;
import org.example.service.JwtService;
import org.example.service.RefreshTokenService;
import org.example.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class TokenController
{

    private AuthenticationManager authenticationManager;


    private RefreshTokenService refreshTokenService;


    private JwtService jwtService;

    private UserDetailServiceImpl userDetailService;

    public TokenController(AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, JwtService jwtService , UserDetailServiceImpl userDetailService) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;

    }

    @PostMapping("/auth/v1/login")
    public ResponseEntity<?> AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUserName(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDto.getUserName());
            String userId = userDetailService.getUserByUsername(authRequestDto.getUserName());
            if(Objects.nonNull(userId) && Objects.nonNull(refreshToken)){
                return new ResponseEntity<>(JwtResponseDto.builder()
                        .accessToken(jwtService.GenerateToken(authRequestDto.getUserName()))
                        .refreshToken(refreshToken.getToken())
                        .build(), HttpStatus.OK);
            }

        }
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/auth/v1/refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){



        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getRefreshToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
