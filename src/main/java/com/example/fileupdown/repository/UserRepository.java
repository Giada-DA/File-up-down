package com.example.fileupdown.repository;

import com.example.fileupdown.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
