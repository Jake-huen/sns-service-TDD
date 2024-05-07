package com.heon.sns.controller.response;

import com.heon.sns.model.User;
import com.heon.sns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class UserJoinResponse {

    private Integer id;
    private String userName;
    private UserRole role;

    public static UserJoinResponse fromUser(User user) {
        return new UserJoinResponse(
                user.getId(),
                user.getUserName(),
                user.getUserRole()
        );
    }
}
