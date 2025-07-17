package com.example.userservice.service;

import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElse(null);
        if (user == null) {
            return null;
        }
        return userMapper.mapUserToUserResponse(user);
    }

    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            User existingUser = userRepository.findByEmail(request.getEmail());
            return userMapper.mapUserToUserResponse(existingUser);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setKeyCloakId(request.getKeyCloakId());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = userRepository.save(user);
        return userMapper.mapUserToUserResponse(savedUser);
    }

    public Boolean existByUserId(String userId) {
        return userRepository.existsById(userId);
    }

    public Boolean existByKeycloakId(String userId) {
        return userRepository.existsByKeyCloakId(userId);
    }
}
