package com.inventorymanagement.service;

import com.inventorymanagement.model.User;
import com.inventorymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Znajduje użytkownika na podstawie nazwy użytkownika
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);  // Rzutowanie (User) jest zbędne
    }

    // Zapisuje nowego użytkownika
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
