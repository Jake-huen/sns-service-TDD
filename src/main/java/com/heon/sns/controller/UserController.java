package com.heon.sns.controller;

import com.heon.sns.controller.request.UserJoinRequest;
import com.heon.sns.controller.request.UserLoginRequest;
import com.heon.sns.controller.response.AlarmResponse;
import com.heon.sns.controller.response.Response;
import com.heon.sns.controller.response.UserJoinResponse;
import com.heon.sns.controller.response.UserLoginResponse;
import com.heon.sns.exception.ErrorCode;
import com.heon.sns.exception.SnsApplicationException;
import com.heon.sns.model.User;
import com.heon.sns.model.entity.UserEntity;
import com.heon.sns.service.AlarmService;
import com.heon.sns.service.UserService;
import com.heon.sns.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AlarmService alarmService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getName(), request.getPassword());
        UserJoinResponse response = UserJoinResponse.fromUser(user);
        return Response.success(response);
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("/alarms")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        // 여기서 Authentication을 그냥 사용할 수도 있지만, 이미 Authentication에는 JWT Token으로 DB 조회를 한번 한, User 정보가 들어있다.
        // service단에서 또 User DB를 조회하는 것은 중복이기 때문에 바로 User를 꺼내서 사용할 수 있다.
        // User user = (User) authentication.getPrincipal();
        User user = ClassUtils.getSafeCaseInstance(authentication.getPrincipal(), User.class)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, "Casting to UserEntity Class Failed"));
        return Response.success(userService.alarmList(user.getId(), pageable).map(AlarmResponse::fromAlarm));
    }

    @GetMapping("/alarm/subscribe")
    public SseEmitter subscribe(Authentication authentication) {
        User user = ClassUtils.getSafeCaseInstance(authentication.getPrincipal(), User.class)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, "Casting to UserEntity Class Failed"));
        return alarmService.connectAlarm(user.getId());
    }
}
