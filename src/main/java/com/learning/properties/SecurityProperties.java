package com.learning.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security")
public record SecurityProperties(
    JWT jwt,
    Cookies cookies
) {
    public record JWT(
            String secret,
            String issuer,
            Long accessTtlSeconds,
            Long refreshTtlSeconds
    ){ }
    public record Cookies(
        String  refreshTokenName,
        Boolean isSecure,
        Boolean isHttpOnly,
        String sameSite,
        String domain,
        String path
    ){ }
}
