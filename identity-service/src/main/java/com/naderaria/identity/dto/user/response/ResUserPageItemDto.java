package com.naderaria.identity.dto.user.response;

import com.naderaria.commoncore.dto.response.PageItem;

public record ResUserPageItemDto(
        Long id,
        String username,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        String city,
        String phoneNumber,
        boolean enabled)
        implements PageItem {
}
