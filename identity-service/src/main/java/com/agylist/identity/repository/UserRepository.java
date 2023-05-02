package com.agylist.identity.repository;

import com.agylist.identity.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserCredential, UUID> {
    Optional<UserCredential> findByName(String username);
}
