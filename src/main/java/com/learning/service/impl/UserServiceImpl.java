package com.learning.service.impl;

import com.learning.entity.User;
import com.learning.repository.UserRepository;
import com.learning.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isEmpty()){
            throw new UsernameNotFoundException("Username not found");
        }
        return userOpt.get();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void thirdPartyRegistration(User user) {
        if(ObjectUtils.isEmpty(user)) {
            return;
        }
        if(userRepository.existsByUsername(user.getUsername())) {
            return;
        }
        userRepository.save(user);
    }
}
