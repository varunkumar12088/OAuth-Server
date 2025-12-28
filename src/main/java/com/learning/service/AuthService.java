package com.learning.service;

import com.learning.dto.AuthResponse;
import com.learning.dto.LoginRequest;
import com.learning.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {


    AuthResponse login(LoginRequest loginRequest);

    AuthResponse logout();

    AuthResponse refreshToken();

    AuthResponse register(UserDto user);

}
