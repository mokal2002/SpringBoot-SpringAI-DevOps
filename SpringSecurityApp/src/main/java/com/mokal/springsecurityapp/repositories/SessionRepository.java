package com.mokal.springsecurityapp.repositories;

import com.mokal.springsecurityapp.entities.Session;
import com.mokal.springsecurityapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}