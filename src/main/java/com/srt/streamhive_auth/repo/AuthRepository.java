package com.srt.streamhive_auth.repo;

import com.srt.streamhive_auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, Long> {
}
