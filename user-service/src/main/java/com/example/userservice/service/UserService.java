package com.example.userservice.service;

import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = userRepository.save(user);
        return userMapper.mapUserToUserResponse(savedUser);
    }

    public Boolean existByUserId(String userId) {
        return userRepository.existsById(userId);
    }
}
