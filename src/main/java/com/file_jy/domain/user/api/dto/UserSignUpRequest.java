package com.file_jy.domain.user.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserSignUpRequest(
        @NotBlank String loginId,
        @NotBlank String username,
        @NotBlank String password
){}
