package com.file_jy.domain.common.jpa.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Email {

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Column(nullable = false, unique = true, name = "email")
    private final String value;

    protected Email() {
        this.value = null;
    }

    public Email(String value) {
        if (value == null || ! value.matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("잘못된 이메일 형식입니다. 입력값 -> " + value);
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
