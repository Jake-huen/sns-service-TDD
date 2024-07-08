package com.heon.sns.repository;

import com.heon.sns.model.entity.LikeEntity;
import com.heon.sns.model.entity.PostEntity;
import com.heon.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {

    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

//    @Query("SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post=:post")
//    Integer countByPost(@Param("post") PostEntity post);

    long countByPost(PostEntity post);

    List<LikeEntity> findAllByPost(PostEntity post);

    @Transactional
    @Modifying
    @Query("DELETE LikeEntity entity WHERE entity.post = :post")
    void deleteAllByPost(@Param("post") PostEntity postEntity); // JPA에서 제공하는 delete는 데이터를 가지고 온 다음 삭제하기 때문에 비효율적이다.
}
