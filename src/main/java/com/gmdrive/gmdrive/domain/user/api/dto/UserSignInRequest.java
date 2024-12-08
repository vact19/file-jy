package com.gmdrive.gmdrive.domain.user.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserSignInRequest(
        @NotBlank String email,
        @NotBlank String password
) {}
