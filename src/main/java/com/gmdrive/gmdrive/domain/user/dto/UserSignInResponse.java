package com.gmdrive.gmdrive.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmdrive.gmdrive.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
public class UserSignInResponse {
    @JsonProperty("user")
    public final UserSignInDto userSignInDto;

    public static UserSignInResponse from(User user) {
        return new UserSignInResponse(
                UserSignInDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .build()
        );
    }

    public static class UserSignInDto {
        public final Long id;
        public final String username;

        @Builder(access = AccessLevel.PRIVATE)
        private UserSignInDto(Long id, String username) {
            this.id = id;
            this.username = username;
        }
    }
}
