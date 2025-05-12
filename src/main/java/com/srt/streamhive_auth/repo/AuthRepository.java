package com.srt.streamhive_auth.repo;

import com.srt.streamhive_auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}
