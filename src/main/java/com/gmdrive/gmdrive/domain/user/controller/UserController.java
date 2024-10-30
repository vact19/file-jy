package com.gmdrive.gmdrive.domain.user.controller;

import com.gmdrive.gmdrive.api.ResponseTemplate;
import com.gmdrive.gmdrive.domain.user.dto.UserSignInRequest;
import com.gmdrive.gmdrive.domain.user.dto.UserSignInResponse;
import com.gmdrive.gmdrive.domain.user.entity.User;
import com.gmdrive.gmdrive.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseTemplate<UserSignInResponse> handleSignIn(
            @RequestBody @Valid UserSignInRequest userSignInRequest
    ) {
        User user = userService.signUp(userSignInRequest);
        UserSignInResponse userSignInResponse = UserSignInResponse.from(user);

        return new ResponseTemplate<>(
                HttpStatus.CREATED,
                String.format("회원 '%s' 가입 완료", userSignInResponse.userSignInDto.username),
                userSignInResponse
        );
    }
}