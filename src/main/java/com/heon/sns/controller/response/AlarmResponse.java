package com.heon.sns.controller.response;

import com.heon.sns.model.Alarm;
import com.heon.sns.model.AlarmArgs;
import com.heon.sns.model.AlarmType;
import com.heon.sns.model.User;
import com.heon.sns.model.entity.AlarmEntity;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class AlarmResponse {

    private Integer id;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AlarmResponse fromAlarm(Alarm alarm) {
        return new AlarmResponse(
                alarm.getId(),
                alarm.getAlarmType(),
                alarm.getArgs(),
                alarm.getAlarmType().getAlarmText(),
                alarm.getCreatedAt(),
                alarm.getUpdatedAt()
        );
    }
}
