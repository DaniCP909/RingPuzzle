package com.example.arcadiarest.arcadiarest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.arcadiarest.arcadiarest.models.User;

public interface IUserRepository extends JpaRepository<User, Long>{

}
