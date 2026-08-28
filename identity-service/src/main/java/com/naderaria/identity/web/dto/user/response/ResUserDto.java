package com.naderaria.identity.web.dto.user.response;

import com.naderaria.identity.web.dto.contact_info.response.ResContactInfoDto;
import com.naderaria.identity.web.dto.location_info.response.ResLocationInfoDto;

public record ResUserDto(
        String username,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean enabled,
        ResLocationInfoDto locationInfo,
        ResContactInfoDto contactInfoDto) {
}
