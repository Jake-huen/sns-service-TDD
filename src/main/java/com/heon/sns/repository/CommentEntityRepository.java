package com.heon.sns.repository;

import com.heon.sns.model.entity.CommentEntity;
import com.heon.sns.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);


    @Transactional
    @Modifying
    @Query("DELETE CommentEntity entity where entity.post = :post")
    void deleteAllByPost(@Param("post") PostEntity postEntity);
}
