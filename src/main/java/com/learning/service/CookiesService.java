package com.learning.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookiesService {

    void attachRefreshToken(HttpServletResponse response, String value);

    void clearRefreshCookie(HttpServletResponse response);

    void addNoStoreHeaders(HttpServletResponse response);

    String extractRefreshToken(HttpServletRequest request);
}
