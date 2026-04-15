package com.srtr.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.srtr.models.User;
import com.srtr.repositories.UserRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(String id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }).orElse(null);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

}
