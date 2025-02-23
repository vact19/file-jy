package com.file_jy.domain.user.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.file_jy.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
public class UserSignUpResponse {
    @JsonProperty("user")
    public final UserSignUpDto userSignUpDto;

    public static UserSignUpResponse from(User user) {
        return new UserSignUpResponse(
                UserSignUpDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .build()
        );
    }

    public static class UserSignUpDto {
        public final Long id;
        public final String username;

        @Builder(access = AccessLevel.PRIVATE)
        private UserSignUpDto(Long id, String username) {
            this.id = id;
            this.username = username;
        }
    }
}
