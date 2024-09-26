package com.example.arcadiarest.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.arcadiarest.models.User;

public interface IUserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
