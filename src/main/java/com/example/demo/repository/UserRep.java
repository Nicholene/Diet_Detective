package com.example.demo.repository;

import com.example.demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin
public interface UserRep extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findUserByUserName(String displayName);
}
