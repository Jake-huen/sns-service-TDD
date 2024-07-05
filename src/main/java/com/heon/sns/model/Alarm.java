package com.heon.sns.model;

import com.heon.sns.model.entity.AlarmEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@Getter
public class Alarm {

    private Integer id;
    // private User user;
    private AlarmType alarmType;
    private AlarmArgs args;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Alarm fromEntity(AlarmEntity entity) {
        log.info("==== Call fromEntity ====");
        return new Alarm(
                entity.getId(),
                // User.fromEntity(entity.getUser()),
                entity.getAlarmType(),
                entity.getArgs(),
                entity.getCreatedDate(),
                entity.getModifiedDate()
        );
    }
}
