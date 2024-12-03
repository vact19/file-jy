package com.gmdrive.gmdrive.domain.user.entity;

import com.gmdrive.gmdrive.domain.common.jpa.BaseEntity;
import com.gmdrive.gmdrive.domain.common.jpa.vo.Email;
import com.gmdrive.gmdrive.global.error.errorcode.UserErrorCode;
import com.gmdrive.gmdrive.global.error.exception.business.BusinessException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Email email;
    private String username;
    private String password; // Password VO로 바꿀 생각도 해봐야 함
    private String refreshToken;
    private LocalDateTime refreshTokenExp;

    @Builder
    private User(Email email, String username, String password) {
        validateValues(email, username, password);
        this.email = email;
        this.username = username;
        this.password = password;
        this.refreshToken = null;
        this.refreshTokenExp = null;
    }

    public void signIn(String refreshToken, LocalDateTime refreshTokenExp) {
        this.refreshToken = refreshToken;
        this.refreshTokenExp = refreshTokenExp;
    }

    private static void validateValues(Email email, String username, String password) {
        if (email == null) {
            throw new BusinessException(UserErrorCode.INVALID_EMAIL, email);
        }

        if (username == null
                || username.length() <= 1
                || username.length() >= 20
        ) {
            throw new BusinessException(UserErrorCode.INVALID_USERNAME, username);
        }

        if (password == null) {
            throw new BusinessException(UserErrorCode.INVALID_PASSWORD, password);
        }
    }
}
