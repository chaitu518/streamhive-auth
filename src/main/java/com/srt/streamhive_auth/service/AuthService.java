package com.srt.streamhive_auth.service;

import com.srt.streamhive_auth.model.User;
import com.srt.streamhive_auth.repo.AuthRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private AuthRepository authRepository;
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    public String registerUser(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        User user = authRepository.save(newUser);
        if (user != null) {
            return "User registered successfully";
        }
        else {
            return "User could not be registered";
        }

    }
}
