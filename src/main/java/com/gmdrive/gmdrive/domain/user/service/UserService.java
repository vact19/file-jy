package com.gmdrive.gmdrive.domain.user.service;

import com.gmdrive.gmdrive.domain.common.jpa.vo.Email;
import com.gmdrive.gmdrive.domain.user.dto.UserSignUpRequest;
import com.gmdrive.gmdrive.domain.user.entity.User;
import com.gmdrive.gmdrive.domain.user.repository.UserRepository;
import com.gmdrive.gmdrive.global.error.errorcode.UserErrorCode;
import com.gmdrive.gmdrive.global.error.exception.business.BusinessException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User signUp(UserSignUpRequest userSignUpRequest) {
        Email email = new Email(userSignUpRequest.email());
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(email)
                .username(userSignUpRequest.username())
                .password(userSignUpRequest.password())
                .build();
        return userRepository.save(user);
    }
}
