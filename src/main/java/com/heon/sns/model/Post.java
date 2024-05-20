package com.heon.sns.model;

import com.heon.sns.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class Post {
    private Integer id;
    private String title;
    private String body;
    private User user;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static Post fromEntity(PostEntity entity) {
        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                User.fromEntity(entity.getUser()),
                entity.getCreatedDate(),
                entity.getModifiedDate()
        );
    }

}
