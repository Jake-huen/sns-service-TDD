package com.heon.sns.repository;

import com.heon.sns.model.entity.AlarmEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Integer> {

    // 굳이 UserEntity까지 조회해서 가져와야 할까? -> UserID만으로 가져온다면?
    // Page<AlarmEntity> findAllByUser(UserEntity user, Pageable pageable);

    // N+1 문제 해결을 위한 JOIN FETCH
    @Query("SELECT a FROM AlarmEntity a JOIN FETCH a.user u WHERE u.id = :userId")
    Page<AlarmEntity> findAllByUserId(@Param("userId") Integer userId, Pageable pageable);


}
