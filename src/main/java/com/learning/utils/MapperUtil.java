package com.learning.utils;

import com.learning.dto.UserDto;
import com.learning.entity.User;
import org.modelmapper.ModelMapper;

public class MapperUtil {

    private static final ModelMapper MAPPER = new ModelMapper();

    public static UserDto toDto(User user) {
        return MAPPER.map(user, UserDto.class);
    }

    public static User toEntity(UserDto userDto) {
        return MAPPER.map(userDto, User.class);
    }
}
