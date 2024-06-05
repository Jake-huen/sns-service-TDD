package com.heon.sns.model;

import com.heon.sns.model.entity.CommentEntity;
import com.heon.sns.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class Comment {
    private Integer id;
    private String comment;
    private String userName;
    private Integer postId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static Comment fromEntity(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getComment(),
                entity.getUser().getUserName(),
                entity.getPost().getId(),
                entity.getCreatedDate(),
                entity.getModifiedDate()
        );
    }

}
