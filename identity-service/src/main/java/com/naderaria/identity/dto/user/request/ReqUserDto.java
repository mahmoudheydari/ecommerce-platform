package com.naderaria.identity.dto.user.request;

import com.naderaria.identity.dto.contact_info.request.ReqContactInfoDto;
import com.naderaria.identity.dto.location_info.request.ReqLocationInfoDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

public record ReqUserDto(
        @NotBlank(message = "reqUserDto.username.notBlank")
        @Size(min = 3, max = 50, message = "reqUserDto.username.size") String username,
        @NotBlank(message = "reqUserDto.password.notBlank")
        @Size(min = 3, message = "reqUserDto.password.size") String password,
        @Validated ReqLocationInfoDto locationInfo,
        @Validated ReqContactInfoDto contactInfo) {
}
