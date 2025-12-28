package com.learning.service.impl;

import com.learning.properties.SecurityProperties;
import com.learning.service.CookiesService;
import com.learning.utils.JsonUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CookiesServiceImpl implements CookiesService {

    private final SecurityProperties securityProperties;

    @PostConstruct
    public void init(){
        System.out.println(JsonUtil.toJsonStr(this.securityProperties));
    }

    @Override
    public void attachRefreshToken(HttpServletResponse response, String refreshToken) {
        ResponseCookie responseCookie = ResponseCookie
                .from(securityProperties.cookies().refreshTokenName(), refreshToken)
                .httpOnly(securityProperties.cookies().isHttpOnly())
                .secure(securityProperties.cookies().isSecure())
                .sameSite(securityProperties.cookies().sameSite())
                .domain(securityProperties.cookies().domain())
                .path(securityProperties.cookies().path())
                .maxAge(securityProperties.jwt().refreshTtlSeconds())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    @Override
    public void clearRefreshCookie(HttpServletResponse response) {
        ResponseCookie responseCookie = ResponseCookie
                .from(securityProperties.cookies().refreshTokenName())
                .httpOnly(securityProperties.cookies().isHttpOnly())
                .secure(securityProperties.cookies().isSecure())
                .sameSite(securityProperties.cookies().sameSite())
                .domain(securityProperties.cookies().domain())
                .path(securityProperties.cookies().path())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    @Override
    public void addNoStoreHeaders(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
    }

    @Override
    public String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null){
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie ->
                        cookie.getName().equals(securityProperties.cookies().refreshTokenName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
