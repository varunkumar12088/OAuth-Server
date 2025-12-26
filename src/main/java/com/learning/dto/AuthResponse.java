package com.learning.dto;

public record AuthResponse <T>(
        T data,
        Integer status
){
    public static <T> AuthResponse<T> of(T data,Integer status){
        return new AuthResponse<T>(data, status);
    }
}
