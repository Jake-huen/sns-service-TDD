package com.heon.sns.model;

import com.heon.sns.model.entity.AlarmEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Alarm {

    private Integer id;
    private User user;
    private AlarmType alarmType;
    private AlarmArgs args;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Alarm fromEntity(AlarmEntity entity) {
        return new Alarm(
                entity.getId(),
                User.fromEntity(entity.getUser()),
                entity.getAlarmType(),
                entity.getArgs(),
                entity.getCreatedDate(),
                entity.getModifiedDate()
        );
    }
}
