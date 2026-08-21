package com.naderaria.identity.dto.role.response;

import java.io.Serializable;

public record ResUpdatableRoleDto(
        long id,
        long groupId,
        String groupName,
        String title,
        String description)
        implements Serializable {
}
