package com.heon.sns.repository;

import com.heon.sns.model.entity.LikeEntity;
import com.heon.sns.model.entity.PostEntity;
import com.heon.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {

    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    @Query("SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post=:post")
    Integer countByPost(@Param("post") PostEntity post);

    List<LikeEntity> findAllByPost(PostEntity post);
}
