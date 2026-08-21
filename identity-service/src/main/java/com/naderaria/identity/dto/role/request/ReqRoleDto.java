package com.naderaria.identity.dto.role.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReqRoleDto(
        @NotNull(message = "reqRoleDto.groupId.NotNull") long groupId,
        @NotBlank(message = "reqRoleDto.title.NotBlank")
        @Size(min = 3, max = 255, message = "reqRoleDto.title.Size") String title,
        @Size(min = 3, max = 255, message = "reqRoleDto.description.Max") String description) {
}

