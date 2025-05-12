package com.srt.streamhive_auth.controller;

import com.srt.streamhive_auth.dto.RequestLoginUserDto;
import com.srt.streamhive_auth.dto.RequestRegisterUserDto;
import com.srt.streamhive_auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth/users")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public String register(@RequestBody RequestRegisterUserDto requestRegisterUserDto) {
        return authService.registerUser(requestRegisterUserDto.getEmail(),requestRegisterUserDto.getPassword());
    }
    @PostMapping("/login")
    public String login(@RequestBody RequestLoginUserDto requestLoginUserDtoUserDto) {
        return authService.loginUser(requestLoginUserDtoUserDto.getEmail(),requestLoginUserDtoUserDto.getPassword());
    }
}
