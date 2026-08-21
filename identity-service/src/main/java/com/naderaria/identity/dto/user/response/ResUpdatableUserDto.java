package com.naderaria.identity.dto.user.response;

import com.naderaria.identity.dto.contact_info.response.ResContactInfoDto;
import com.naderaria.identity.dto.location_info.response.ResLocationInfoDto;

public record ResUpdatableUserDto(
        Long id,
        String username,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean enabled,
        ResLocationInfoDto locationInfo,
        ResContactInfoDto contactInfo) {
}
