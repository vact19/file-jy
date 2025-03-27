package com.file_jy.domain.user.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserSignInRequest(
        @NotBlank String loginId,
        @NotBlank String password
) {}
