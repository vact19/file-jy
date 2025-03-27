package com.file_jy.domain.user.entity;

import com.file_jy.domain.common.jpa.BaseEntity;
import com.file_jy.global.error.errorcode.UserErrorCode;
import com.file_jy.global.error.exception.business.BusinessException;
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

    private String loginId;
    private String username;
    private String password; // Password VO로 바꿀 생각도 해봐야 함
    private String refreshToken;
    private LocalDateTime refreshTokenExp;

    @Builder
    private User(String loginId, String username, String password) {
        validateValues(loginId, username, password);
        this.loginId = loginId;
        this.username = username;
        this.password = password;
        this.refreshToken = null;
        this.refreshTokenExp = null;
    }

    public void signIn(String refreshToken, LocalDateTime refreshTokenExp) {
        this.refreshToken = refreshToken;
        this.refreshTokenExp = refreshTokenExp;
    }

    private static void validateValues(String loginId, String username, String password) {
        if (loginId == null
                || loginId.length() <= 1
                || loginId.length() >= 11
        ) {
            throw new BusinessException(UserErrorCode.INVALID_LOGIN_ID, loginId);
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
