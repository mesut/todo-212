package com.todo.service;

import com.todo.dto.UserDto;
import com.todo.model.User;
import com.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void createAndSave(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPasswordHash(encodePassword(userDto));
        userRepository.save(user);
    }

    private String encodePassword(UserDto userDto) {
        return new BCryptPasswordEncoder().encode(userDto.getPassword());
    }

}
