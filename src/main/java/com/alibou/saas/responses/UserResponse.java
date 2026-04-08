package com.alibou.saas.responses;

import com.alibou.saas.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
}
