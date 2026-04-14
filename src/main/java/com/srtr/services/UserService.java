package com.srtr.services;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
}
