package com.srt.streamhive_auth.controller;

import com.srt.streamhive_auth.dto.RequestRegisterUserDto;
import com.srt.streamhive_auth.service.AuthService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    public String register(@RequestBody RequestRegisterUserDto requestRegisterUserDto) {
        return authService.registerUser(requestRegisterUserDto.getEmail(),requestRegisterUserDto.getPassword());
    }
}
