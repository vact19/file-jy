package com.file_jy.domain.user.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserSignUpRequest(
        @NotBlank String email,
        @NotBlank String username,
        @NotBlank String password
){}
