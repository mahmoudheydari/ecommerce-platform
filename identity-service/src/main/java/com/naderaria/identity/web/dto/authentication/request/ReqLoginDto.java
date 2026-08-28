package com.naderaria.identity.web.dto.authentication.request;

import jakarta.validation.constraints.NotBlank;

public record ReqLoginDto(
        @NotBlank(message = "security.username.notBlank") String username,
        @NotBlank(message = "security.password.notBlank") String password) {
}
