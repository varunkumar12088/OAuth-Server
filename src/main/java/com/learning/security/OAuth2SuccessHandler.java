package com.learning.security;

import com.learning.constant.Provider;
import com.learning.entity.User;
import com.learning.service.CookiesService;
import com.learning.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final CookiesService cookiesService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Authentication Success: {}", authentication.getPrincipal());

        Object principal = authentication.getPrincipal();
        OAuth2User oAuth2User = null;

        if(ObjectUtils.isNotEmpty(principal) &&  principal instanceof OAuth2User) {
            oAuth2User = (OAuth2User) principal;
        }

        // Identify user
        String registrationId = "unknown";
        if(ObjectUtils.isEmpty(oAuth2User)) {
            throw new BadRequestException("Invalid username or password");
        }

        if(authentication instanceof OAuth2AuthenticationToken token) {
            registrationId = token.getAuthorizedClientRegistrationId();
        }

        log.info("Registration Id: {}", registrationId);
        log.info("Authentication Success: {}", oAuth2User.getAttributes());
        User user;
        switch (registrationId) {
            case "google" -> {
                String googleId = oAuth2User.getAttributes().getOrDefault("sub", "").toString();
                String email = oAuth2User.getAttributes().getOrDefault("email", "").toString();
                user = User.builder()
                        .username(email)
                        .password("")
                        .provider(Provider.GOOGLE)
                        .role("USER")
                        .build();
            }
            default -> {
                throw new BadRequestException("Invalid username or password");
            }

        }

        userService.thirdPartyRegistration(user);
    }
}
