package com.inventorymanagement.repository;

import com.inventorymanagement.model.User;  // Import poprawnej klasy User z modelu
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

