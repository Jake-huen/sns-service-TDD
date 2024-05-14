package com.heon.sns.controller.response;

import com.heon.sns.model.User;
import com.heon.sns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponse {

    private Integer id;
    private String userName;
    private UserRole role;

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getUserRole()
        );
    }
}
