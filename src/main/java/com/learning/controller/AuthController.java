package com.learning.controller;

import com.learning.dto.AuthResponse;
import com.learning.dto.LoginRequest;
import com.learning.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<AuthResponse> logout() {
        AuthResponse authResponse = authService.logout();
        return ResponseEntity.ok(authResponse);
    }
}
