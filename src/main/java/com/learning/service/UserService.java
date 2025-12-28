package com.learning.service;

import com.learning.entity.User;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    List<User> getUsers();

    void thirdPartyRegistration(User user);
}
