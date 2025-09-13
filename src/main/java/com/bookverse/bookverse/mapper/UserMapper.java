package com.bookverse.bookverse.mapper;

import com.bookverse.bookverse.dto.UserDto;
import com.bookverse.bookverse.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements BaseMapper<UserDto, User> {
    @Override
    public UserDto toDto(User entity) {
        if(entity == null) return null;
        return UserDto.builder().id(entity.getId())
                .username(entity.getName()).build();
    }

    @Override
    public User toEntity(UserDto dto) {
        if(dto ==null) return null;
        return User.builder().id(dto.getId())
                .name(dto.getUsername()).build();
        // List of reviews left to be added in Service layer
    }
}
