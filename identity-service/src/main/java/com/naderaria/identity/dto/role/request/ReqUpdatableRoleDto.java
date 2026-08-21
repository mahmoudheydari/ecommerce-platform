package com.naderaria.identity.dto.role.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReqUpdatableRoleDto(
        @NotNull(message = "reqUpdatableRoleDto.id.NotNull") long id,
        @NotNull(message = "reqUpdatableRoleDto.groupId.NotNull") long groupId,
        @NotBlank(message = "reqUpdatableRoleDto.title.NotBlank")
        @Size(min = 3, max = 255, message = "reqUpdatableRoleDto.title.Size") String title,
        @Size(min = 3, max = 255, message = "reqUpdatableRoleDto.description.Max") String description) {
}