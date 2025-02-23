package com.file_jy.domain.jwt.constant;

import lombok.Getter;

@Getter
public enum AuthScheme {

    BEARER("Bearer");

    private final String label;

    AuthScheme(String label) {
        this.label = label;
    }
}
