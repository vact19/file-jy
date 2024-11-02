package com.gmdrive.gmdrive.domain.user.service;

import com.gmdrive.gmdrive.domain.common.jpa.vo.Email;
import com.gmdrive.gmdrive.domain.jwt.dto.TokenDto;
import com.gmdrive.gmdrive.domain.jwt.service.TokenManager;
import com.gmdrive.gmdrive.domain.user.dto.UserSignInRequest;
import com.gmdrive.gmdrive.domain.user.dto.UserSignUpRequest;
import com.gmdrive.gmdrive.domain.user.entity.User;
import com.gmdrive.gmdrive.domain.user.repository.UserRepository;
import com.gmdrive.gmdrive.global.error.errorcode.UserErrorCode;
import com.gmdrive.gmdrive.global.error.exception.business.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    public User signUp(UserSignUpRequest userSignUpRequest) {
        Email email = new Email(userSignUpRequest.email());
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(email)
                .username(userSignUpRequest.username())
                .password(passwordEncoder.encode(userSignUpRequest.password()))
                .build();
        return userRepository.save(user);
    }

    public TokenDto signIn(UserSignInRequest userSignInRequest) {
        Email email = new Email(userSignInRequest.email());
        String rawPassword = userSignInRequest.password();

        // 비밀번호 매칭 확인
        User user = userRepository.getByEmail(email);
        boolean isPasswordMatched = passwordEncoder.matches(rawPassword, user.getPassword());
        if (! isPasswordMatched) {
            log.warn("비밀번호 검증 실패. source -> {}", rawPassword);
            throw new BusinessException(UserErrorCode.INVALID_PASSWORD, rawPassword);
        }

        TokenDto tokenDto = tokenManager.createTokenDto(user.getId().toString());
        // refresh token 등록
        user.signIn(tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExp());
        userRepository.save(user);
        tokenDto.setUsername(user.getUsername());

        return tokenDto;
    }
}
