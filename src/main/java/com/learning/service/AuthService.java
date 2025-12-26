package com.learning.service;

import com.learning.entity.User;

public interface AuthService {

    public User login(String username, String password);
}
