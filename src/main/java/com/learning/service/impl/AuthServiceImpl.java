package com.learning.service.impl;

import com.learning.dto.AuthResponse;
import com.learning.dto.LoginRequest;
import com.learning.dto.LoginResponse;
import com.learning.dto.UserDto;
import com.learning.service.AuthService;
import com.learning.service.CookiesService;
import com.learning.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CookiesService cookiesService;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        authenticate(loginRequest);
        return authResponse(loginRequest.username());
    }

    @Override
    public AuthResponse logout() {
        HttpServletResponse httpResponse = createResponse();
        cookiesService.clearRefreshCookie(httpResponse);
        AuthResponse<String> response = AuthResponse.of("Logout successfully", 200);
        return response;
    }

    @Override
    public AuthResponse refreshToken() {
        HttpServletRequest httpRequest = createRequest();
        String refreshToken = cookiesService.extractRefreshToken(httpRequest);
        if(!JwtUtils.validateRefreshToken(refreshToken)){
            throw new BadCredentialsException("Invalid refresh token");
        }
        String username = JwtUtils.getUsername(refreshToken);
        return authResponse(username);
    }

    @Override
    public AuthResponse register(UserDto user) {
        return null;
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        } catch (Exception ex){
            throw new BadCredentialsException("Invalid username and password");
        }
    }

    private HttpServletResponse createResponse() {
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        if(ObjectUtils.isNotEmpty(attributes) && attributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) attributes;
            if(ObjectUtils.isNotEmpty(requestAttributes)) {
                return requestAttributes.getResponse();
            }
        }
        throw new BadCredentialsException("Invalid username and password");
    }

    private HttpServletRequest createRequest() {
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        if(ObjectUtils.isNotEmpty(attributes) && attributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) attributes;
            if(ObjectUtils.isNotEmpty(requestAttributes)) {
                return requestAttributes.getRequest();
            }
        }
        throw new BadCredentialsException("Invalid username and password");
    }

    private AuthResponse<LoginResponse> authResponse(String username) {
        HttpServletResponse httpResponse = createResponse();
        String accessToken = JwtUtils.generateAccessToken(username);
        String refreshToken = JwtUtils.generateRefreshToken(username);
        LoginResponse  loginResponse = new LoginResponse(accessToken, refreshToken);
        AuthResponse<LoginResponse> response = AuthResponse.of(loginResponse, 200);
        cookiesService.attachRefreshToken(httpResponse, refreshToken);
        cookiesService.addNoStoreHeaders(httpResponse);
        return response;
    }
}
