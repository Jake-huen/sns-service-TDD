package com.heon.sns.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(indexes = {
        @Index(name = "user_id_idx", columnList = "user_id"),
        @Index(name = "post_id_idx", columnList = "post_id")
})
public class CommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(name = "comment")
    private String comment;

    public static CommentEntity of(UserEntity userEntity, PostEntity postEntity, String comment) {
        CommentEntity entity = new CommentEntity();
        entity.setUser(userEntity);
        entity.setPost(postEntity);
        entity.setComment(comment);
        return entity;
    }
}
