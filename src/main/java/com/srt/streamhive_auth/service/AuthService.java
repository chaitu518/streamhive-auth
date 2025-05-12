package com.srt.streamhive_auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srt.streamhive_auth.model.User;
import com.srt.streamhive_auth.repo.AuthRepository;
import com.srt.streamhive_auth.security.JwtUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final String TOPIC = "register-user";
    private AuthRepository authRepository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    private KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public AuthService(AuthRepository authRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, KafkaTemplate<String, String> kafkaTemplate) {

        this.authRepository = authRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.kafkaTemplate = kafkaTemplate;
    }
    public String registerUser(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        User user = authRepository.save(newUser);
        if (user != null) {
            String token = jwtUtil.generateToken(user.getEmail());
            try {
                Map<String, Object> payload = new HashMap<>();
                payload.put("id", user.getId());

                Map<String, String> kafkaUser = new HashMap<>();
                kafkaUser.put("email", email);

                payload.put("user", kafkaUser);
                String jsonMessage = objectMapper.writeValueAsString(payload);
                kafkaTemplate.send(TOPIC, jsonMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
