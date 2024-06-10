package com.heon.sns.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {
    // 알람을 발생시킨 사람
    private Integer fromUserId;
    private Integer targetId;

//    private List<Integer> targetIds;
//    private Integer alarmOccurId;
}

// comment : 00씨가 새 코멘트를 작성했습니다 -> postId, commentId
// 00외 2명이 새 코멘트를 작성했습니다. -> commentId, commentId

// 필드가 많아지면 계속해서 증가할 수 있다.
// 데이터를 null로 넣게 되면 굉장히 비효율적이라서(쓸모없는 column을 추가해야되기 때문에) argument로 대처하는 방법이 조금 더 편리하다.