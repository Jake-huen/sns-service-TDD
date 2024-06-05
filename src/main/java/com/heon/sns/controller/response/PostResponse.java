package com.heon.sns.controller.response;

import com.heon.sns.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostResponse {
    private Integer id;
    private String title;
    private String body;
    private UserResponse user;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                UserResponse.fromUser(post.getUser()),
                post.getCreatedDate(),
                post.getModifiedDate()
        );
    }
}
