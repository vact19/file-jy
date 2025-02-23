package com.file_jy.domain.user.api.controller;

import com.file_jy.api.ResponseTemplate;
import com.file_jy.domain.jwt.dto.TokenDto;
import com.file_jy.domain.user.api.dto.UserSignInRequest;
import com.file_jy.domain.user.api.dto.UserSignUpRequest;
import com.file_jy.domain.user.api.dto.UserSignUpResponse;
import com.file_jy.domain.user.entity.User;
import com.file_jy.domain.user.service.UserService;
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
    public ResponseTemplate<UserSignUpResponse> handleSignUp(
            @RequestBody @Valid UserSignUpRequest userSignUpRequest
    ) {
        User user = userService.signUp(userSignUpRequest);
        UserSignUpResponse userSignUpResponse = UserSignUpResponse.from(user);

        return new ResponseTemplate<>(
                HttpStatus.CREATED,
                String.format("회원 '%s' 가입 완료", userSignUpResponse.userSignUpDto.username),
                userSignUpResponse
        );
    }

    @PostMapping("/users/sign-in")
    public ResponseTemplate<TokenDto> handleSignIn(
            @RequestBody @Valid UserSignInRequest userSignInRequest
    ) {
        TokenDto tokenDto = userService.signIn(userSignInRequest);
        return new ResponseTemplate<>(
                HttpStatus.OK,
                String.format("회원 '%s' 로그인 완료", tokenDto.getUsername()),
                tokenDto
        );
    }
}