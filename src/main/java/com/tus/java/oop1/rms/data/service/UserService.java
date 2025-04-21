package com.tus.java.oop1.rms.data.service;

import com.tus.java.oop1.rms.data.models.GoldUser;
import com.tus.java.oop1.rms.data.models.User;
import com.tus.java.oop1.rms.data.repository.api.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Transactional
    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId).map( user -> {
            if (user.isGoldUser())
                return new GoldUser(user);
            else return user;
        });
    }

    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}
