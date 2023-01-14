package com.codebuffer.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codebuffer.userservice.Entity.UserData;
import com.codebuffer.userservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserData save(UserData user) {
        log.info("Invoked method save from UserService class");
        return userRepository.save(user);
    }

    public UserData findByUserId(Long id) {
        log.info("Invoked method UserData from UserService class");
        return userRepository.findByUserId(id);
    }

}
