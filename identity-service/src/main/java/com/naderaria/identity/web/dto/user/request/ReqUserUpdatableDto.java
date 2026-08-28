package com.naderaria.identity.web.dto.user.request;

import com.naderaria.identity.web.dto.contact_info.request.ReqUpdatableContactInfoDto;
import com.naderaria.identity.web.dto.location_info.request.ReqUpdatableLocationInfoDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

public record ReqUserUpdatableDto(
        @NotBlank(message = "reqUserDto.username.notBlank")
        @Size(min = 3, max = 50, message = "reqUserDto.username.size") String username,
        @Validated ReqUpdatableLocationInfoDto locationInfo,
        @Validated ReqUpdatableContactInfoDto contactInfo) {
}
