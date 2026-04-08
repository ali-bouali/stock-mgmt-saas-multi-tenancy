package com.alibou.saas.mappers;

import com.alibou.saas.entities.User;
import com.alibou.saas.requests.UserRequest;
import com.alibou.saas.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User toEntity(final UserRequest request) {
        return User.builder()
                   .username(request.getUsername())
                   .email(request.getEmail())
                   .password(request.getPassword())
                   .role(request.getRole())
                   .firstName(request.getFirstName())
                   .lastName(request.getLastName())
                   .build();
    }

    public UserResponse toResponse(final User user) {
        return UserResponse.builder()
                .id(user.getId())
                   .username(user.getUsername())
                   .email(user.getEmail())
                   .password(user.getPassword())
                   .role(user.getRole())
                   .firstName(user.getFirstName())
                   .lastName(user.getLastName())
                   .build();
    }
}
