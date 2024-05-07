package com.heon.sns.controller;

import com.heon.sns.controller.request.UserJoinRequest;
import com.heon.sns.controller.response.Response;
import com.heon.sns.controller.response.UserJoinResponse;
import com.heon.sns.model.User;
import com.heon.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getUserName(), request.getPassword());
        UserJoinResponse response = UserJoinResponse.fromUser(user);
        return Response.success(response);
    }

    @PostMapping("/login")
    public Response<?> login() {
        return null;
    }
}
