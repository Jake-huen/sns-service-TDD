package com.heon.sns.model;

import com.heon.sns.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class User {

    private Integer id;
    private String userName;
    private String password;
    private UserRole userRole;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static User fromEntity(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getRole(),
                entity.getCreatedDate(),
                entity.getModifiedDate()
        );
    }
}
