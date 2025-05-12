package com.srt.streamhive_auth.service;

import com.srt.streamhive_auth.model.User;
import com.srt.streamhive_auth.repo.AuthRepository;
import com.srt.streamhive_auth.security.JwtUtil;
import com.srt.streamhive_auth.security.SecurityConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private AuthRepository authRepository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    public AuthService(AuthRepository authRepository, JwtUtil jwtUtil) {

        this.authRepository = authRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public String registerUser(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        User user = authRepository.save(newUser);
        if (user != null) {
            String token = jwtUtil.generateToken(user.getEmail());
            return token;
        }
        else {
            return "Not registered";
        }

    }

    public String loginUser(String email, String password) {
        Optional<User> userOpt = authRepository.findByEmail(email);
        if (userOpt.isPresent() &&
                new BCryptPasswordEncoder().matches(password, userOpt.get().getPassword())) {

            String token = jwtUtil.generateToken(email);
            return token;
        }
        return "Invalid Credentials";
    }
}
