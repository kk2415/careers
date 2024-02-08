package com.levelup.job.presentation.controller.v1.dto;

import com.levelup.job.util.jwt.AccessToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginDto {

    public record LoginRequest(
            @NotNull @NotBlank
            String username,

            @NotNull
            String password
    ) {

        public static LoginRequest of(String username, String password) {
            return new LoginRequest(username, password);
        }
    }

    public record LoginResponse(
            AccessToken accessToken,
            String role
    ) {

        public static LoginResponse of(AccessToken accessToken, String role) {
            return new LoginResponse(accessToken, role);
        }
    }
}
