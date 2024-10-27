package com.ringpuzzle.ringpuzzlerest.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ringpuzzle.ringpuzzlerest.models.User;

public interface IUserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
