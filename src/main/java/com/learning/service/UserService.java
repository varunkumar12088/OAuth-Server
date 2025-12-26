package com.learning.service;

import com.learning.entity.User;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface UserService {

    List<User> getUsers();
}
