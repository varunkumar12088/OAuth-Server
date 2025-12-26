package com.learning.utils;

import java.util.UUID;

public class JwtUtils {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String COLON = ":";
    private static final Long ACCESS_TOKEN_EXP_IN = 600000L;
    private static final Long REFRESH_TOKEN_EXP_IN = 2 * ACCESS_TOKEN_EXP_IN;

    public static String generateAccessToken(String username){
        return generateToken(ACCESS_TOKEN, username);
    }

    public static String generateRefreshToken(String username){
        return generateToken(REFRESH_TOKEN, username);
    }

    public static boolean validateAccessToken(String token){
        if(token == null){
            return false;
        }

        return false;
    }


    private static String generateToken(String constant, String username){
        StringBuffer sb = new StringBuffer();
        sb.append(constant);
        sb.append(COLON);
        sb.append(System.currentTimeMillis());
        sb.append(COLON);
        sb.append(UUID.randomUUID());
        sb.append(COLON);
        sb.append(username);
        return sb.toString();
    }

    private static Long getGeneratedTime(String token){
        String generatedTimeStr = token.split(COLON)[1];
        return Long.parseLong(generatedTimeStr);
    }
}
