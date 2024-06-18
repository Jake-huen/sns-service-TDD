package com.heon.sns.repository;

import com.heon.sns.model.entity.AlarmEntity;
import com.heon.sns.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Integer> {

    // 굳이 UserEntity까지 조회해서 가져와야 할까? -> UserID만으로 가져온다면?
    // Page<AlarmEntity> findAllByUser(UserEntity user, Pageable pageable);

    Page<AlarmEntity> findAllByUser(Integer userId, Pageable pageable);


}
